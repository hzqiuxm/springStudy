package org.study.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.study.model.Zoo
import org.study.service.IBaseService
import org.study.util.JsonUtil

/**
 * Created by Administrator on 2016/4/15.
 */
@RestController
@groovy.util.logging.Log
class BaseController{
    @Autowired
    final IBaseService service

    @RequestMapping(value = "/zoos",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Zoo>> selects() {
        log.info("request --> list of all ${simpleName}")

        returnList(service.findAll())
    }

    @RequestMapping(value = "/zoos/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Zoo> select(@PathVariable id) {
        log.info("request --> get ${simpleName} by id=${id}")

        returnOne(service.find(id))
    }

    @RequestMapping(value = "/zoos",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Zoo> create(@RequestBody Zoo model) {
        log.info("request --> create ${simpleName},params is ${JsonUtil.prettyPrint(model)}");

        returnOne(service.create(model))
    }

    @RequestMapping(value = "/zoos",method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Zoo> put(@RequestBody Zoo model) {
        log.info("request --> patch update ${simpleName} by ${JsonUtil.prettyPrint(model)}")

        returnOne(service.update(model))
    }
    @RequestMapping(value = "/zoos",method = RequestMethod.PATCH,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Zoo> patch(@RequestBody Zoo model) {
        log.info("request --> patch update ${simpleName} by ${JsonUtil.prettyPrint(model)}")

        returnOne(service.patch(model))
    }

    @RequestMapping(value = "/zoos/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Zoo> delete(@PathVariable id){
        log.info("delete ${simpleName} by id=${id}");

        service.delete(id)

        log.info("response --> delete ${simpleName} sucess");
        return new ResponseEntity<Zoo>(HttpStatus.NO_CONTENT);
    }
    //------工具方法或属性------
    private String simpleName = "zoo"
    private ResponseEntity<Zoo> returnOne(Zoo o) {
        if (o) {
            log.info("response --> ${JsonUtil.prettyPrint(o)}")
            return new ResponseEntity<Zoo>(o, HttpStatus.OK)
        } else {
            log.info("response --> null")
            return new ResponseEntity<Zoo>(HttpStatus.NOT_FOUND)
        }
    }
    private ResponseEntity<List<Zoo>> returnList(List<Zoo> list) {
        if (list) {
            log.info("response --> ${JsonUtil.prettyPrint(list)}")
            return new ResponseEntity<List<Zoo>>(list, HttpStatus.OK)
        } else {
            log.info("response --> null")
            return new ResponseEntity<List<Zoo>>(HttpStatus.NOT_FOUND)
        }
    }
}
