package org.study.util

import groovy.json.JsonOutput

/**
 * Created by Administrator on 2016/4/15.
 */
class JsonUtil {
    static String prettyPrint(o){
        use(JsonOutput){
            o.toJson().prettyPrint()
        }
    }
}
