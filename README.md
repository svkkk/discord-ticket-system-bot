# Discord TicketSystem Bot

This is a bot that allows you to create a ticket to contact the discord server administration by its user

### Example action

![.](https://i.imgur.com/2uJFal7.png)

### If the user presses the button

![.](https://i.imgur.com/WZgrZ9W.png)

### After completing the form, a channel will be opened

![.](https://i.imgur.com/kfQhvXF.png)

### A private message will be sent to the user

![.](https://i.imgur.com/3tspuMd.png)




## Example config

```java
    public Config() {
        embedColorAll = "#000000";
        needRole = new NeedRole();
        commands = new Commands();
        buttons = new Buttons();
        messages = new Messages();
        categories = new Categories();
    }
    public static class NeedRole {
        public ArrayList<Long> all_perms = new ArrayList<Long>() {{
            add(123123123123L); 
        }};
    }
    public static class Commands {
        public String sender_command = "example_command";
    }
    public static class Buttons {
        public String ticket_start_button_id = "example_button";
        public String ticket_close_button_id = "example_button";
    }
    public static class Messages {
        public String ticket_start_button_message = "example_message";
        public String ticket_start_embed_title = "example_message";
        public String ticket_start_embed_description = "example_message";
        public String ticket_created_message = "example_message";
        public String ticket_close_button_message = "example_message2";
        public String ticket_close_message = "example_message";
    }
    public static class Categories {
        public String tickets_category = "123123123123123L";
    }
```

## I encourage you to use the code, if you find an error please contact me

## Contact with me
My private discord: svk#1066

[My Discord server](https://dc.dxsbots.pl)
