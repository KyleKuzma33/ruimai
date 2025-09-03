package com.wong.question.user.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.*;
import com.wong.question.user.service.*;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author wqc
 * @Date 2024/8/12 17:05
 */
@Api(tags = "题目")
@RestController
@RequestMapping("subject")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    @Resource
    private UserAnswerLogService userAnswerLogService;

    @Resource
    private UserService userService;

    @Resource
    private ScoreService scoreService;

    // 题目增删改查 题目倒计时字段
    @ApiOperation(value = "新增题目或更新题目")
    @PostMapping("addOrUpdate")
    public R add(@RequestBody SubjectEntity subjectEntity) {
        return subjectService.addSubject(subjectEntity);
    }

    @ApiOperation(value = "删除题目")
    @GetMapping("del")
    public R delById(Integer id) {
        return subjectService.delById(id);
    }


    // 题目列表 带分页
    @ApiOperation(value = "题目列表带分页")
    @GetMapping("getSubPage")
    public R getSubPage(int current, int size, String keyword) {
        return R.success(subjectService.getListPage(current, size));
    }

    // 题目下发到客户端（用于轮询最新题目）
    @ApiOperation(value = "获取题目")
    @GetMapping("getSubNow")
    public R getSubNow() {
        return subjectService.getSubNow();
    }

    @ApiOperation(value = "设置最新题目")
    @GetMapping("setSubNow")
    public R setSubNow(Integer subId) {
        return subjectService.setSubNow(subId);
    }

    @ApiOperation(value = "提交答案")
    @PostMapping("addAnswer")
    public R addAnswer(@RequestBody UserAnswerLogEntity userAnswerLogEntity) {
        return userAnswerLogService.addAnswer(userAnswerLogEntity);
    }

    @ApiOperation("新增轮次得分")
    @PostMapping("addScore")
    public R addScore(@RequestBody ScoreEntity scoreEntity) {
        UserEntity userEntity = userService.getById(scoreEntity.getUserId());
        scoreEntity.setUserName(userEntity.getName());
        scoreEntity.setCreateTime(new Date());
        return scoreService.saveOrUpdate(scoreEntity) ? R.success("新增成功") : R.error("新增失败");
    }

    @ApiOperation("本题得分统计")
    @GetMapping("getSubjectScore")
    public R getSubjectScore(Integer subjectId) {
        return R.success(userAnswerLogService.getAllBySubjectId(subjectId));
    }

    @ApiOperation("积分统计")
    @GetMapping("getScore")
    public R getScore() {
        return scoreService.getScoreList();
    }

    @ApiOperation("下一题")
    @GetMapping("nextSubject")
    public R nextSubject(Integer currentSubjectId) {
        return subjectService.setSubNow(currentSubjectId);
    }

    @ApiOperation("开始答题")
    @GetMapping("startSub")
    public R startSub(Integer subjectId) {
        return subjectService.startSub(subjectId);
    }

    @ApiOperation("用户信息导出")
    @GetMapping("/export")
    public void export(HttpServletResponse httpServletResponse) throws IOException {
        List<UserAnswerLogEntity> allList = userAnswerLogService.getAll();


        ExcelWriter excelWriter = ExcelUtil.getWriter(true);

        excelWriter.addHeaderAlias("id", "名次");
        //将数据模型中的username字段映射为Excel表格中的用户名列。
        excelWriter.addHeaderAlias("userName", "队名");
        excelWriter.addHeaderAlias("sum", "总分");


        /*
        写出数据，本方法只是将数据写入Workbook中的Sheet，并不写出到文件
        写出的起始行为当前行号，可使用getCurrentRow()方法调用，根据写出的的行数，当前行号自动增加
        样式为默认样式，可使用getCellStyle()方法调用后自定义默认样式
        data - 数据
        isWriteKeyAsHead - 是否强制写出标题行（Map或Bean）
         */
        //只保留别名的数据
        for (int i = 1; i <= allList.size(); i++) {
            excelWriter.setOnlyAlias(true);
            allList.get(i-1).setId(i);
        }

        excelWriter.setOnlyAlias(true);
        excelWriter.write(allList, true);


        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");
        String filename = URLEncoder.encode("排名统计.xlsx", "UTF-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + filename);

        final ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        /*
        将Excel Workbook刷出到输出流
            Parameters:
            out - 输出流
            isCloseOut - 是否关闭输出流
         */
        excelWriter.flush(outputStream,true);
        outputStream.close();
        // 刷新缓冲区，确保数据发送到客户端
        httpServletResponse.flushBuffer();
        excelWriter.close();

    }


    // 提交答案接口。单选题正确加10分，多选题正确加10分，判断题正确加10分。题目倒计时。
    // 用户列表，用户怎删改查，用户答题记录
    // 积分统计：提交的选项，总积分，队名（用户名）
    // 排名统计：名次、队名、总积分
    // 统计导出：名次，队名、总积分
}