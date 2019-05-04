package dp.com.nabbtabase.utils;

public class ConfigurationFile {

    public static class UrlConstants {

        public static final String BASE_URL = "http://151.106.52.107:2020/";//"http://dp-itc.com:8888/";
        public static final String COUNTRIES_URL = "api/utilities/countries";
        public static final String CATEGORIES_URL = "api/utilities/categories";
        public static final String LOGIN_URL = "api/login";
        public static final String OFFERS = "api/search/offers";
        public static final String BESET_SELLER = "api/search/best-seller";
        public static final String CLIENT_REGISTER_URL = "api/register";
        public static final String CHANGE_PASSWORD_URL = "api/change-password";
        public static final String FORGET_PASSWORD_URL = "api/phone/send";
        public static final String RESET_PASSWORD_URL = "api/forget/reset/{token}";
        public static final String SHIPPING_ADDRESS_URL = "api/address";
        public static final String UPLOAD_IMAGE = "api/media";
        public static final String SERVICE_REQUEST = "api/service-request";
        public static final String ADD_TO_CART_ULR = "api/cart";
        public static final String CART_PRODUCTS = "api/cart";
        public static final String DELETE_ITEM_FROM_CART = "api/cart/{id}";
        public static final String CHECK__CODE = "api/forget";
        public static final String EDIT_PROFILE_URL = "api/profile";
        public static final String SERVICES_URL = "api/utilities/services";
        public static final String UPDATE_CART_ITEM = "api/cart/{id}";
        public static final String SERVICES_HISTORY = "api/service-request";
        public static final String CREATE_ORDER = "api/order";
        public static final String GET_PRODUCT_COMMENTS = "api/product/{id}/comments";
        public static final String PRODUCTS_SEARCH = "api/search";
        public static final String CREATE_COMMENT = "api/rate";
        public static final String GET_ORDER_HISTORY_URL = "api/order";
        public static final String GET_HISTORY_PRODUCT = "api/order/{id}";
        public static final String UPDATE_ADDRESS = "api/address/{id}";
        public static final String DELETE_ORDER = "api/order/{id}";
        public static final String ACTIVATE_PHONE = "api/activate/phone";
        public static final String SEND_MAIL = "api/activate/email/send";
        public static final String CHECK_EXIST_MAIL ="api/check/email";
    }

    public static class Constants {
        public static final String API_KEY = "MUaJWwkyZOZKnbvZczGoFDt0sLyeS0eCkoKXtam00nobixvPC2BV2rcP3TKJSLYU";
        public static final String CONTENT_TYPE = "application/json";
        public static final int SUCCESS_CODE = 200;
        public static final int SUCCESS_CODE_SECOND = 201;
        public static final int INVALED_DATA_CODE = 422;
        public static final int SECONDS_WAIT = 429;
        public static final int INVALED_EMAIL_PASSWORD = 401;
        public static final int SUSBENDED = 417;
        public static final int ALREADY_ACTIVATED = 403;
        public static final int TRY_LATER = 429;
        //////////////////////////////////////////////////////////////////////////
        public static final int FILL_ALL_DATA_ERROR_CODE = -601;
        public static final int INVALED_EMAIL = -602;
        public static final int PASSWORD_LENGTH_ERROR = -603;
        public static final int PASSWORD_CONFIRMATION_ERROR = -604;
        public static final int EXISET_MAIL_CODE = -605;
        public static final int NO_INTERNET_CONNECTION_CODE = -606;
        public static final int EXISET_PHONE_CODE = -607;
        public static final int ACTIVE_ACCOUNT_ERROR = -608;
        ////////////////////////////////////////////////////////////////////
        public static final int PLACE_PICKER_REQUEST = 1;
        public static final int CALL_LOGIN = 2;
        public static final int SELECT_COUNTRY = 3;
        public static final int SIGNUP = 4;
        public static final int SHOW_PROGRESS_DIALOG = 5;
        public static final int SHOW_COUNTRY_DIALOG = 6;
        public static final int MOVE_TO_CODE_ACTIVITY = 7;
        public static final int MOVE_TO_SPECIALIZATION_ACT = 8;
        public static final int SPECIALIZATION_ADAPTER = 9;
        public static final int SKIP = 10;
        public static final int REGISTER_STEP2 = 11;
        public static final int ENTER_MAIL = 12;
        public static final int ERROR_DIALOG_REQUEST = 9001;
        public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
        public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;


    }

    public static class SharedPrefConstants {
        public static final String SHARED_PREF_NAME = "TADAWAY_SHARED_PREF";
        public static final String APP_LANGUAGE = "APP_LANGUAGE";
    }

    public static class IntentConstants {
        public static final String ORDER_ID = "ORDERID";
        public static final String SERVICE_REQUEST_1STEP_DATA = "servicestep1data";
        public static final String RESET_PASSWORD_DATA = "RESETPASSWORD";
        public static final String REGISTER_STEP1_DATA = "REGISTERSTEP1DATA";
        public static final String PRODUCT_DATA = "PRODUCTDATA";
        public static final String ADDRESS_ID = "ADDRESSID";
        public static final String SERVICE_DATA = "SERVICE";
        public static final String SERVICE_ID = "SERVICEID";
        public static final String ADDRESS_REQUEST = "ADDRESSREQUEST";
        public static final String LONGITUDE_REQUEST = "longitude";
        public static final String LATITUDE_REQUEST = "latitude";
        public static final String SPECIALIZATION_DATA = "SPECIALIZATION";
        public static final String LOGIN_INFO = "LOGININFO";
        public static final String REQUEST_ITEM_DATA = "ITEMREQUEST";
        public static final String SELECTED_ADDRESS="address";
        public static final String SELECTED_LAT="lat";
        public static final String SELECTED_LANG="lang";
        public static final int REQUEST_CODE_COUNTRY = 111;
        public static final int REQUEST_CODE_CITY = 222;
        public static final int REQUEST_CODE_MAP = 333;
        public static final int REQUEST_CODE_SPECIALIZATION = 444;
        public static final int START_PLACE_PICKER=555;
    }
}
