package com.jvpass.monitor.equipment.device

import com.jvpass.monitor.common.DeviceUpdateMessage
import com.jvpass.monitor.equipment.configuration.RabbitConfiguration
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.jvm.Throws
import kotlin.random.Random

@Component
class DeviceUpdateScheduler {

    @Autowired
    private val rabbitTemplate: RabbitTemplate? = null

    @Scheduled(fixedDelay = 5000)
    fun scheduleDeviceUpdate() {
        sendDeviceUpdate(getRandomMessage())
    }

    private fun sendDeviceUpdate(message: DeviceUpdateMessage) {
        rabbitTemplate?.convertAndSend(
                RabbitConfiguration.fanoutExchangeName,
                "",
                message
        )
    }

    private fun getRandomMessage() =
        DeviceUpdateMessage().apply {
            idSensor = Random.nextInt(0, 10).toString()
            leitura = Random.nextFloat()
        }
}

