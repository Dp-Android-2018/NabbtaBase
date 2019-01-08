package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import dp.com.nabbtabase.servise.model.pojo.PaymentMethod;

public class CreateOrderResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("code")
    private String code;

    @SerializedName("date")
    private String date;

    @SerializedName("email")
    private String email;

    @SerializedName("total")
    private double total;

    @SerializedName("payment_method")
    private PaymentMethod paymentMethod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
