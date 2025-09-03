//package com.wong.question.user.controller;
//
//import com.wechat.pay.java.service.payments.h5.H5Service;
//import com.wechat.pay.java.service.payments.h5.model.*;
//import com.wechat.pay.java.service.payments.model.Transaction;
//import com.wechat.pay.java.core.notification.NotificationParser;
//import com.wechat.pay.java.core.notification.RequestParam;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/pay/h5")
//@RequiredArgsConstructor
//public class H5payController {
//
//    private final H5Service h5Service;
//    private final NotificationParser notificationParser;
//    private final String appId;
//
//    /**
//     * H5 下单
//     */
//    @PostMapping("/prepay")
//    public PrepayResp prepay(@RequestBody PrepayReq req, HttpServletRequest httpReq) {
//        // 获取客户端 IP
//        String clientIp = req.getClientIp();
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = httpReq.getRemoteAddr();
//        }
//
//        // 构建 H5 下单请求
//        PrepayRequest request = new PrepayRequest();
//        request.setAppid(appId);
//        request.setMchid(req.getMchid());
//        request.setOutTradeNo(req.getOutTradeNo());
//        request.setDescription(req.getDescription());
//        request.setNotifyUrl(req.getNotifyUrl());
//
//        // 金额 (0.2.17 版本使用单独的 Amount 类)
//        Amount amount = new Amount();
//        amount.setTotal(req.getTotal()); // 单位：分
//        amount.setCurrency("CNY");
//        request.setAmount(amount);
//
//        // 场景信息
//        SceneInfo scene = new SceneInfo();
//        scene.setPayerClientIp(clientIp);
//
//        H5Info h5Info = new H5Info();
//        h5Info.setType(req.getType() == null ? "Wap" : req.getType());
//        h5Info.setAppName(req.getAppName());
//        h5Info.setAppUrl(req.getAppUrl());
//        scene.setH5Info(h5Info);
//
//        request.setSceneInfo(scene);
//
//        // 调用 H5Service 下单
//        PrepayResponse prepayResponse = h5Service.prepay(request);
//
//        // 构建返回对象
//        PrepayResp resp = new PrepayResp();
//        resp.setOutTradeNo(req.getOutTradeNo());
//        resp.setH5Url(prepayResponse.getH5Url());
//        return resp;
//    }
//
//
//    /**
//     * 查询订单（按商户单号）
//     */
//    @GetMapping("/query/{outTradeNo}")
//    public Transaction query(@PathVariable String outTradeNo) {
//        QueryOrderByOutTradeNoRequest req = new QueryOrderByOutTradeNoRequest();
//        req.setOutTradeNo(outTradeNo);
//        // 如果 RSAAutoCertificateConfig 已经配置了 mchid，可以不设置
//        req.setMchid("你的商户号");
//        return h5Service.queryOrderByOutTradeNo(req);
//    }
//
//
//    /**
//     * 关闭订单
//     */
//    @PostMapping("/close")
//    public String close(@RequestBody CloseReq req) {
//        CloseOrderRequest closeReq = new CloseOrderRequest();
//        closeReq.setOutTradeNo(req.getOutTradeNo());
//        closeReq.setMchid(req.getMchid());
//        h5Service.closeOrder(closeReq);
//        return "ok";
//    }
//
//    /**
//     * 支付回调接口
//     */
//    @PostMapping("/notify")
//    public String notify(HttpServletRequest request) throws IOException {
//        String body;
//        try (BufferedReader reader = request.getReader()) {
//            body = reader.lines().collect(Collectors.joining("\n"));
//        }
//
//        RequestParam param = new RequestParam.Builder()
//                .serialNumber(request.getHeader("Wechatpay-Serial"))
//                .nonce(request.getHeader("Wechatpay-Nonce"))
//                .signature(request.getHeader("Wechatpay-Signature"))
//                .timestamp(request.getHeader("Wechatpay-Timestamp"))
//                .body(body)
//                .build();
//
//        // 验签 + 解密
//        Transaction transaction = notificationParser.parse(param, Transaction.class);
//
//        // TODO: 根据 transaction.getOutTradeNo() 更新订单状态（幂等处理）
//        System.out.println("支付成功：" + transaction);
//
//        // 微信回调要求返回 success 表示已接收
//        return "success";
//    }
//
//    // ===== DTO =====
//    @Data
//    public static class PrepayReq {
//        private String outTradeNo;
//        private String description;
//        private Integer total; // 分
//        private String clientIp;
//        private String appName;
//        private String appUrl;
//        private String type;
//        private String mchid;
//        private String notifyUrl;
//    }
//
//    @Data
//    public static class PrepayResp {
//        private String outTradeNo;
//        private String h5Url;
//    }
//
//    @Data
//    public static class CloseReq {
//        private String outTradeNo;
//        private String mchid;
//    }
//}
