package ru.underfish.app.database.entities

import jakarta.persistence.*
import ru.underfish.app.database.entities.enums.AttendanceStatus
import lombok.Data

@Entity
@Data
@Table(name = "event_attendances")
data class EventAttendance(

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) val user: User,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "event_id", nullable = false) val event: Event
) : AbstractEntity() {

    @Enumerated(EnumType.STRING)
    @Column(
        name = "attendance_status",
        columnDefinition = "VARCHAR(20) DEFAULT 'pending' CHECK (attendance_status IN ('pending', 'confirmed', 'cancelled', 'attended'))"
    )
    var status: AttendanceStatus = AttendanceStatus.pending

}
