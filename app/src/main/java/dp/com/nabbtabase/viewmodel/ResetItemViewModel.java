package dp.com.nabbtabase.viewmodel;

import dp.com.nabbtabase.servise.model.pojo.ResetProduct;

public class ResetItemViewModel {

    ResetProduct product;

    public ResetItemViewModel(ResetProduct product) {
        this.product = product;
    }

    public void setProduct(ResetProduct product) {
        this.product = product;
    }

    public String getName() {
        return product.getName();
    }

    public String getQuantity() {
        return String.valueOf(product.getQuantity());
    }

    public String getPrice() {
        return String.valueOf(product.getPrice()) + "$";
    }

    public String getTotal() {
        return String.valueOf(product.getTotal()) + "$";
    }
}
