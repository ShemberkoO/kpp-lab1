package src;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import enums.PaymentMethod;

import java.lang.reflect.Type;
import java.util.List;

public class OrderDeserializer implements JsonDeserializer<Order> {
    @Override
    public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String orderID = jsonObject.get("orderID").getAsString();
        String customerName = jsonObject.get("customerName").getAsString();
        List<Product> products = context.deserialize(jsonObject.get("products"),
                new TypeToken<List<Product>>() {}.getType());
        String paymentMethodStr = jsonObject.get("paymentMethod").getAsString();

        PaymentMethod paymentMethod = PaymentMethod.fromString(paymentMethodStr);

        return new Order(orderID, customerName, products, paymentMethodStr);
    }
}