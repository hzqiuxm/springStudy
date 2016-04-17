package org.study.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.study.model.Animal
import org.study.model.Zoo
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Created by Yuri on 2016/4/17.
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

        ObjectMapper mapper = new ObjectMapper()
        entity.body == mapper.writeValueAsString(zoos)
    }

    def "Select"() {
        when:
        ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:8080/zoos/1001", String.class)

        then:
        entity.statusCode == HttpStatus.OK

        ObjectMapper mapper = new ObjectMapper()
        entity.body == mapper.writeValueAsString(zoos.find{
            it.id == '1001'
        })
    }

    def "Create"() {
        when:
        HttpHeaders headers = new HttpHeaders()
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8")
        headers.setContentType(type)
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
        ObjectMapper mapper = new ObjectMapper()
        Zoo zoo = new Zoo(id:"1003",name:"Zoo3")
        String json = mapper.writeValueAsString(zoo)
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers)
        ResponseEntity entity = new RestTemplate().postForEntity("http://localhost:8080/zoos",httpEntity, String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == json
    }

    /**
     * RestTemplate put 没有返回值，所以，使用exchange
     */
    def "Put"() {
        when:
        HttpHeaders headers = new HttpHeaders()
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8")
        headers.setContentType(type)
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
        ObjectMapper mapper = new ObjectMapper()
        Zoo zoo = new Zoo(id:"1001",name:"Zoo3")
        String json = mapper.writeValueAsString(zoo)
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers)
        ResponseEntity entity = new RestTemplate().exchange("http://localhost:8080/zoos",HttpMethod.PUT,httpEntity, String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == json
    }

    def "Patch"() {
        when:
        HttpHeaders headers = new HttpHeaders()
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8")
        headers.setContentType(type)
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE)
        ObjectMapper mapper = new ObjectMapper()
        Zoo zoo = new Zoo(id:"1001",name:"Zoo3")
        String json = mapper.writeValueAsString(zoo)
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers)

        RestTemplate restTemplate = new RestTemplate()
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory()
        requestFactory.setConnectTimeout(1000)
        restTemplate.setRequestFactory(requestFactory)
        ResponseEntity entity = restTemplate.exchange("http://localhost:8080/zoos",HttpMethod.PATCH,httpEntity, String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == json
    }

    def "Delete"() {
        when:
        ResponseEntity entity = new RestTemplate().exchange("http://localhost:8080/zoos/1001",HttpMethod.DELETE,null,HttpStatus.class)

        then:
        entity.statusCode == HttpStatus.NO_CONTENT
    }
}
