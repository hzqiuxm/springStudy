package org.study.service.impl

import org.springframework.stereotype.Service
import org.study.model.Animal
import org.study.model.Zoo
import org.study.service.IBaseService

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
class BaseServiceImpl implements IBaseService{
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
    @Override
    def List<Zoo> findAll() {
        return zoos
    }

    @Override
    def Zoo find(id) {
        zoos.find{
            it.id == id
        }
    }

    @Override
    def Zoo create(Zoo model) {
        Zoo o = model.clone()
        if(!o.id){
            o.id = UUID.randomUUID().toString()
        }
        zoos << o
        return o
    }
    @Override
    Zoo update(Zoo model) {
        Zoo o = model.clone()
        zoos.collect{
            if(it.id == model.id){
                it = o
            }
        }
        return o
    }
    /**
     * 这段代码逻辑有错误，没有实现部分更新
     * @param model
     * @return
     */
    @Override
    Zoo patch(Zoo model) {
        Zoo o = null
        zoos.collect{
            if(it.id == model.id){
                Map map =  it.properties + model.properties
                map.remove("class")
                o = map as Zoo
                it = o
            }
        }
        return o
    }

    @Override
    void delete(id) {
        zoos.remove(zoos.find{
            it.id == id
        })
    }
}
