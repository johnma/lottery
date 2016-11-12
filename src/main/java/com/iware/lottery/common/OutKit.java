package com.iware.lottery.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wuhao on 16/11/5.
 */
public class OutKit {

    public static void writeJson(HttpServletResponse response,String jsonStr){
        response.setContentType("application/json;charset = utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out;
        try {
            out = response.getWriter();
            out.print(jsonStr);
            out.flush();
            out.close();
        } catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
