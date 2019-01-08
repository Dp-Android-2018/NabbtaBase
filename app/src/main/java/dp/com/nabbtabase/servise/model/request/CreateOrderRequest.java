package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class CreateOrderRequest {

    @SerializedName("address_id")
    private int addressId;

    @SerializedName("billing_address_id")
    private int billingAddressId;

    @SerializedName("payment_id")
    private int paymentId;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
