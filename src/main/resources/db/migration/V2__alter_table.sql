ALTER TABLE users
    ADD role VARCHAR(20) DEFAULT 'user' CHECK (role IN ('user', 'admin'));

ALTER TABLE users
    ALTER COLUMN role SET NOT NULL;

ALTER TABLE event_attendances
    ALTER COLUMN attendance_status SET DEFAULT 'pending' CHECK (attendance_status IN ('pending', 'confirmed', 'cancelled', 'attended'));

ALTER TABLE events
    ALTER COLUMN event_status SET DEFAULT 'draft' CHECK (event_status IN ('published', 'cancelled', 'completed', 'draft'));

ALTER TABLE locations
    DROP COLUMN latitude;

ALTER TABLE locations
    DROP COLUMN longitude;

ALTER TABLE locations
    ADD latitude DECIMAL(11, 8) DEFAULT 0 NOT NULL;

ALTER TABLE locations
    ADD longitude DECIMAL(11, 8) DEFAULT 0 NOT NULL;

ALTER TABLE community_member
    ALTER COLUMN member_role SET DEFAULT 'member' CHECK (member_role IN ('member', 'organizer'));

ALTER TABLE events
    DROP COLUMN price;

ALTER TABLE events
    ADD price DECIMAL(10, 2) DEFAULT 0;

ALTER TABLE notifications
    ALTER COLUMN priority SET DEFAULT 'normal' CHECK (priority IN ('low', 'normal', 'high'));

ALTER TABLE notifications
    ALTER COLUMN type SET DEFAULT 'system' CHECK (type IN ('event', 'system', 'message'));