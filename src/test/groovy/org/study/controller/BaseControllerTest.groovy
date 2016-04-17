package org.study.controller

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.study.util.JsonUtil
import spock.lang.AutoCleanup
import spock.lang.Shared

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import org.study.model.Animal
import org.study.model.Zoo
import spock.lang.Specification


/**
 * Created by Administrator on 2016/4/17.
 */
class BaseControllerTest extends Specification {
    @Shared
    @AutoCleanup
    ConfigurableApplicationContext context

    private List<Zoo> zoos = [
            new Zoo(id:"1001",name:"Zoo1",animals:[
                    new Animal(id:"100101",name:"子鼠"),
                    new Animal(id:"100102",name:"丑牛"),
                    new Animal(id:"100103",name:"寅虎")
            ]),
            new Zoo(id:"1002",name:"Zoo2",animals:[
                    new Animal(id:"100201",name:"子鼠"),
                    new Animal(id:"100202",name:"辰龙"),
                    new Animal(id:"100203",name:"巳蛇")
            ])
    ]

    void setupSpec() {
        Future<ConfigurableApplicationContext> future = Executors
                .newSingleThreadExecutor().submit(
                new Callable() {
                    @Override
                    public ConfigurableApplicationContext call() throws Exception {
                        return SpringApplication.run(App.class)
                    }
                })
        context = future.get(60, TimeUnit.SECONDS)
    }

    def "Selects"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8080/zoos", String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == """[{"id":"1001","name":"Zoo1","animals":[{"id":"100101","name":"子鼠"},{"id":"100102","name":"丑牛"},{"id":"100103","name":"寅虎"}]},{"id":"1002","name":"Zoo2","animals":[{"id":"100201","name":"子鼠"},{"id":"100202","name":"辰龙"},{"id":"100203","name":"巳蛇"}]}]"""
    }

}
