package com.jvpass.monitor.dashboard.device

import com.jvpass.monitor.common.DeviceUpdateMessage
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class DeviceUpdateReceiver {

    @RabbitListener(queues = ["dashboard-queue"])
    fun receive(payload: DeviceUpdateMessage) {
        println("Sensor: ${payload.idSensor}, leitura: ${payload.leitura}")
    }
}
