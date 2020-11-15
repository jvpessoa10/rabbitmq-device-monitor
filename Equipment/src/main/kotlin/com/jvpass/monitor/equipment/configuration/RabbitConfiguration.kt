package com.jvpass.monitor.equipment.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitConfiguration {

    companion object {
        const val fanoutExchangeName = "equipment-status-fanout-exchange"
        const val dashboardQueueName = "dashboard-queue"
        const val historyQueueName = "history-queue"
    }

    @Bean
    fun dashboardQueue(): Queue {
        return Queue(dashboardQueueName, false)
    }

    @Bean
    fun historyQueue(): Queue {
        return Queue(historyQueueName, false)
    }

    @Bean
    fun exchange(): FanoutExchange {
        return FanoutExchange(fanoutExchangeName)
    }

    @Bean
    fun historyBinding(historyQueue: Queue, exchange: FanoutExchange): Binding {
        return BindingBuilder.bind(historyQueue).to(exchange)
    }

    @Bean
    fun dashboardBinding(dashboardQueue: Queue, exchange: FanoutExchange): Binding {
        return BindingBuilder.bind(dashboardQueue).to(exchange)
    }

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun producerJackson2MessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }
}
