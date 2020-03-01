/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.server.engine.scripts;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.bitstwinkle.jelly.bc.facade.domains.BillingTask;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class InProportionScriptTest {

  @Test
  public void testExecute() {
    Assertions.fail();
  }



  public static void main(String[] args){
    String tpl = "{\n"
        + "  \"src\": \"srcAccid\",\n"
        + "  \"transfers\": [\n"
        + "    {\n"
        + "      \"dest\": \"srcAccid\",\n"
        + "      \"amount\": \"99\"\n"
        + "    },\n"
        + "    {\n"
        + "      \"dest\": \"srcAccid\",\n"
        + "      \"amount\": \"payAmount*0.04\"\n"
        + "    },\n"
        + "    {\n"
        + "      \"dest\": \"srcAccid\",\n"
        + "      \"amount\": \"8\"\n"
        + "    },    \n"
        + "  ]\n"
        + "}";

    InProportionScript inProportionScript = new InProportionScript(tpl);
    Map<String, Object> context = new HashMap<>();
    context.put("srcAccid", "aaaaa");
    context.put("payAmount", 10000.00);
    BillingTask billingTask = inProportionScript.execute(context);
    System.out.println(billingTask);
  }
}