package com.xwder.app.modules.jrebel.controller;

import com.xwder.app.common.result.CommonResult;
import com.xwder.app.modules.jrebel.JrebelUtil.JrebelSign;
import com.xwder.app.modules.jrebel.util.RsaSign;
import com.xwder.app.utils.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * jrebel 激活服务
 * 来源： https://gitee.com/gsls200808/JrebelLicenseServerforJava.git
 *
 * @author xwder
 * @date 2020/11/9 17:37
 */
@Controller
public class JrebelActivateController {

    @ResponseBody
    @PostMapping(value = {"/jrebel/leases", "/agent/leases"})
    public Object activateJrebleLeases(HttpServletRequest request, HttpServletResponse response) {
        String clientRandomness = request.getParameter("randomness");
        String username = request.getParameter("username");
        String guid = request.getParameter("guid");
        boolean offline = Boolean.parseBoolean(request.getParameter("offline"));
        String validFrom = "null";
        String validUntil = "null";
        if (offline) {
            String clientTime = request.getParameter("clientTime");
            String offlineDays = request.getParameter("offlineDays");
            //long clinetTimeUntil = Long.parseLong(clientTime) + Long.parseLong(offlineDays)  * 24 * 60 * 60 * 1000;
            long clinetTimeUntil = Long.parseLong(clientTime) + 180L * 24 * 60 * 60 * 1000;
            validFrom = clientTime;
            validUntil = String.valueOf(clinetTimeUntil);
        }

        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"id\": 1,\n" +
                "    \"licenseType\": 1,\n" +
                "    \"evaluationLicense\": false,\n" +
                "    \"signature\": \"OJE9wGg2xncSb+VgnYT+9HGCFaLOk28tneMFhCbpVMKoC/Iq4LuaDKPirBjG4o394/UjCDGgTBpIrzcXNPdVxVr8PnQzpy7ZSToGO8wv/KIWZT9/ba7bDbA8/RZ4B37YkCeXhjaixpmoyz/CIZMnei4q7oWR7DYUOlOcEWDQhiY=\",\n" +
                "    \"serverRandomness\": \"H2ulzLlh7E0=\",\n" +
                "    \"seatPoolType\": \"standalone\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"offline\": " + String.valueOf(offline) + ",\n" +
                "    \"validFrom\": " + validFrom + ",\n" +
                "    \"validUntil\": " + validUntil + ",\n" +
                "    \"company\": \"Administrator\",\n" +
                "    \"orderId\": \"\",\n" +
                "    \"zeroIds\": [\n" +
                "        \n" +
                "    ],\n" +
                "    \"licenseValidFrom\": 1490544001000,\n" +
                "    \"licenseValidUntil\": 1691839999000\n" +
                "}";

        Map map = JSONUtil.parseMap(jsonStr);
        if (clientRandomness == null || username == null || guid == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return CommonResult.failed();
        } else {
            JrebelSign jrebelSign = new JrebelSign();
            jrebelSign.toLeaseCreateJson(clientRandomness, guid, offline, validFrom, validUntil);
            String signature = jrebelSign.getSignature();
            map.put("signature", signature);
            map.put("company", username);
            return map;
        }
    }

    @ResponseBody
    @PostMapping(value = {"/jrebel/leases/1", "/agent/leases/1"})
    public Object jrebelLeases1Handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        String username = request.getParameter("username");
        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"msg\": null,\n" +
                "    \"statusMessage\": null\n" +
                "}\n";
        Map map = JSONUtil.parseMap(jsonStr);
        if (username != null) {
            map.put("company", username);
        }
        return map;
    }

    @ResponseBody
    @PostMapping(value = {"/jrebel/validate-connection"})
    public Object jrebelValidateHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String jsonStr = "{\n" +
                "    \"serverVersion\": \"3.2.4\",\n" +
                "    \"serverProtocolVersion\": \"1.1\",\n" +
                "    \"serverGuid\": \"a1b4aea8-b031-4302-b602-670a990272cb\",\n" +
                "    \"groupType\": \"managed\",\n" +
                "    \"statusCode\": \"SUCCESS\",\n" +
                "    \"company\": \"Administrator\",\n" +
                "    \"canGetLease\": true,\n" +
                "    \"licenseType\": 1,\n" +
                "    \"evaluationLicense\": false,\n" +
                "    \"seatPoolType\": \"standalone\"\n" +
                "}\n";
        Map map = JSONUtil.parseMap(jsonStr);
        return map;
    }

    @ResponseBody
    @PostMapping(value = {"/rpc/ping.action"})
    public Object pingHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String salt = request.getParameter("salt");

        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<PingResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></PingResponse>";
            String xmlSignature = RsaSign.Sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            return body;
        }
        return CommonResult.failed();
    }

    @ResponseBody
    @PostMapping(value = {"/rpc/obtainTicket.action"})
    public Object obtainTicketHandler(HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        SimpleDateFormat fm = new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss Z", Locale.ENGLISH);
        String date = fm.format(new Date()) + " GMT";
        //response.setHeader("Date", date);
        //response.setHeader("Server", "fasthttp");
        String salt = request.getParameter("salt");
        String username = request.getParameter("userName");
        String prolongationPeriod = "607875500";
        if (salt == null || username == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<ObtainTicketResponse><message></message><prolongationPeriod>" + prolongationPeriod + "</prolongationPeriod><responseCode>OK</responseCode><salt>" + salt + "</salt><ticketId>1</ticketId><ticketProperties>licensee=" + username + "\tlicenseType=0\t</ticketProperties></ObtainTicketResponse>";
            String xmlSignature = RsaSign.Sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            return body;
        }
        return CommonResult.failed();
    }

    @ResponseBody
    @PostMapping(value = {"/rpc/releaseTicket.action"})
    public Object releaseTicketHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String salt = request.getParameter("salt");

        if (salt == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String xmlContent = "<ReleaseTicketResponse><message></message><responseCode>OK</responseCode><salt>" + salt + "</salt></ReleaseTicketResponse>";
            String xmlSignature = RsaSign.Sign(xmlContent);
            String body = "<!-- " + xmlSignature + " -->\n" + xmlContent;
            return body;
        }
        return CommonResult.failed();
    }

    @ResponseBody
    @PostMapping(value = {"/guid"})
    public Object guidHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String body = UUID.randomUUID().toString();
        return body;
    }
}
