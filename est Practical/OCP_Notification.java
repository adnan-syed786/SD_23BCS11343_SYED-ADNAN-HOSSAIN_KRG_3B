// Step 1: Common Interface

interface Notification {
    void send();
}

--------------------------------------------------

// Step 2: Individual Notification Classes

class EmailNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending notification via EMAIL");
    }
}

class SMSNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending notification via SMS");
    }
}

class WhatsAppNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending notification via WHATSAPP");
    }
}

--------------------------------------------------

// Step 3: Service Class depends on abstraction

class NotificationService {

    public void notifyUser(Notification notification) {
        notification.send();
    }
}

--------------------------------------------------

// Step 4: Main Class

public class Main {
    public static void main(String[] args) {

        NotificationService service = new NotificationService();

        // Email Notification
        Notification email = new EmailNotification();
        service.notifyUser(email);

        // SMS Notification
        Notification sms = new SMSNotification();
        service.notifyUser(sms);

        // WhatsApp Notification
        Notification whatsapp = new WhatsAppNotification();
        service.notifyUser(whatsapp);

    }
}