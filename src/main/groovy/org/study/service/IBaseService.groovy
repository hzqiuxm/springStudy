package org.study.service

import org.study.model.Zoo

/**
 * Created by Administrator on 2016/4/15.
 * 思考：
 * 1、GET "/zoos/{id}/animals"
 *   应该归属于"List<T> findAll(id)"，还是"List<T> find(id)"？
 *
 * 2、GET "/zoos/{zooId}/animals/{animalId}"
 *   1) Zoo和Animal的主键都是id，这样写访问路径，对么？
 *   2) 否则，怎么区分这两个参数？
 *   3) 如果animals还有复杂属性呢？继续访问，如何操作？
 */
interface IBaseService {
    /**
     * 查找对象列表
     * 示例：
     * GET "/zoos"
     * @return
     */
    List<Zoo> findAll()
    /**
     * 根据id查找对象
     * 示例：
     * GET "/zoos/{id}"
     * @param id
     * @return
     */
    Zoo find(id)
    /**
     * 新建对象
     * 示例：
     * POST "/zoos"
     * @param model
     * @return
     */
    Zoo create(Zoo model)
//    /**
//     * 更新对象
//     * 示例：
//     * PUT "/zoos"
//     * @param model
//     * @return
//     */
//    def Zoo update(Zoo model)
//    /**
//     * 部分更新对象
//     * 示例：
//     * PATCH "/zoos"
//     * @param model
//     * @return
//     */
//    def Zoo patch(Zoo model)
    /**
     * 根据id删除对象
     * 示例：
     * DELETE "/zoos/{id}"
     * @param id
     */
    void delete(id)
}
