ALTER TABLE messages ADD COLUMN user_id UUID;
ALTER TABLE messages ADD CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE messages ADD COLUMN room_id UUID;
ALTER TABLE messages ADD CONSTRAINT FK_room_chat FOREIGN KEY (room_id) REFERENCES room_chats (room_id);