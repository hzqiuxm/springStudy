package org.study.model

import groovy.transform.AutoClone

/**
 * Created by Administrator on 2016/4/15.
 */
@AutoClone
class Zoo implements Serializable{
    String id
    String name
    List<Animal> animals
}
