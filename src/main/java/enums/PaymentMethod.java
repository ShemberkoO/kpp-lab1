package enums;

public enum PaymentMethod {
    CASH("CASH"),
    CARD("CARD");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static PaymentMethod fromString(String method) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.method.equalsIgnoreCase(method)) {
                return paymentMethod;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + method);
    }
}
