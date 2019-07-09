package com.fukun.commons.constants;

/**
 * rabbitmq相关的常量
 *
 * @author tangyifei
 * @date 2019年7月9日09:15:42
 */
public final class RabbitMqConstants {

    /*********************************** 消息队列常量开始 **************************/
    /**
     * topic 类型的交换机的名字
     */
    public final static String TOPIC_EXCHANGE_NAME = "topic.order";

    /**
     * direct 类型的交换机的名字
     */
    public final static String DIRECT_EXCHANGE_NAME = "amq.direct";

    /**
     * 基本消息（字符串形式）
     */
    public final static String TOPIC_QUEUE_NAME_BASIC = "topic.queue.basic.*";

    /**
     * 实体对象类型的消息
     */
    public final static String TOPIC_QUEUE_NAME_OBJECT = "topic.queue.object.*";

    /**
     * direct 类型的交换机相关的队列名称
     */
    public static final String DIRECT_QUEUE_NAME = "direct.queue";

    /**
     * topic 类型交换机与队列绑定的key-基本信息
     */
    public final static String TOPIC_ROUTING_KEY_BASIC = "topic.queue.basic.#";

    /**
     * topic 类型交换机与队列绑定的key-对象
     */
    public final static String TOPIC_ROUTING_KEY_OBJECT = "topic.queue.object.#";

    /**
     * direct 类型交换机与队列绑定的key
     */
    public static final String DIRECT_ROUTING_KEY = "direct.queue";

    public final static String BASIC_ROUTING_KEY = "topic.queue.basic.1";

    public final static String OBJECT_ROUTING_KEY = "topic.queue.object.order";
    /*********************************** 消息队列常量结束 **************************/
}