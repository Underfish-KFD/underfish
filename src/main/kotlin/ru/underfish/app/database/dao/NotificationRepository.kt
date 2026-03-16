package ru.underfish.app.database.dao

import ru.underfish.app.database.entities.Notification


interface NotificationRepository : AbstractRepository<Notification> {
	fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<Notification>
}
