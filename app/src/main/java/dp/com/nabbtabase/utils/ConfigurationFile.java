package dp.com.nabbtabase.utils;

public class ConfigurationFile {

    public static class UrlConstants{
       public static final String BASE_URL="http://dp-itc.com:4321/";//"http://dp-itc.com:8888/";
       public static final String COUNTRIES_URL ="api/utilities/countries";
       public static final String CATEGORIES_URL="api/utilities/categories";
       public static final String LOGIN_URL="api/login";
       public static final String CLIENT_REGISTER_URL="api/clients/register";
       public static final String COMPANY_REGISTER_URL="api/companies/register";
       public static final String CHANGE_PASSWORD_URL="api/change-password";
       public static final String FORGET_PASSWORD_URL="api/forget";
       public static final String UPDATE_METADATA="api/companies/company-meta-data";
       public static final String CHECK_MAIL="api/check/email";
       public static final String CHECK_PHONE="api/check/phone";
       public static final String CHECK_ACTIVATION_STATUS="api/activate/check";
       public static final String SEND_ACTIVATION_MAIL="api/activate/email/send";
       public static final String PHONE_ACTIVATION="api/activate/phone";
       public static final String SEND_ACTIVATION_CODE="api/activate/phone/send";
       public static final String CHECK_ACTIVATION_CODE="api/activate/phone/sent";
       public static final String CREATE_CLIENT_RESERVATION="api/clients/reservation";
       public static final String COMPANY_APPROVE_RESERVATION="api/companies/reservation";
       public static final String GET_RESERVATION_URL="api/reservations/{status}";
       public static final String GET_RESERVATION_DETAIL_URL="api/reservation/{id}";
       public static final String GET_COMPANY_DETAIL="api/company/{id}/data";
       public static final String GET_COMPANY_COMMENTS="api/company/{id}/comments";
       public static final String COMPANY_SEARCH="api/search";
       public static final String CREATE_COMMENT="api/clients/comments";
       public static final String GET_SPECIALIZATION_URL="api/utilities/specializations";
       public static final String DELETE_RESERVATION_URL="api/companies/reservation/{id}";
       }

    public static class Constants{
        public static final String API_KEY="MUaJWwkyZOZKnbvZczGoFDt0sLyeS0eCkoKXtam00nobixvPC2BV2rcP3TKJSLYUVTesDXhTmTfsr9Qu0TK145yCJOPIKwsvEqvWEUfSOun7PZyMWfTlBWeKB56OYiHm";
        public static final String CONTENT_TYPE="application/json";
        public static final String CLINIC="CLINIC";
        public static final String SPA="SPA";
        public static final String HOSPITAL="HOSPITAL";
        public static final String FIRM="FIRM";
        public static final String CLIENT="client";
        public static final String COMPANY="company";
        public static final String ACCEPT="approved";
        public static final String PEND="pending";

        public static final int SUCCESS_CODE=200;
        public static final int SUCCESS_CODE_SECOND=201;
        public static final int INVALED_DATA_CODE=422;
        public static final int INVALED_EMAIL_PASSWORD=401;
        public static final int SUSBENDED=417;
        public static final int ALREADY_ACTIVATED=403;
        public static final int TRY_LATER=429;
//////////////////////////////////////////////////////////////////////////
        public static final int FILL_ALL_DATA_ERROR=-601;
        public static final int INVALED_EMAIL=-602;
        public static final int PASSWORD_LENGTH_ERROR=-603;
        public static final int PASSWORD_CONFIRMATION_ERROR=-604;
        public static final int EXISET_MAIL_CODE=-605;
        public static final int NO_INTERNET_CONNECTION_CODE=-606;
        public static final int EXISET_PHONE_CODE=-607;
        public static final int ACTIVE_ACCOUNT_ERROR=-608;



        ////////////////////////////////////////////////////////////////////
        public static final int GETIMAGE=1;
        public static final int PICK_IMAGE_REQUEST=2;
        public static final int SELECT_COUNTRY=3;
        public static final int MOVE_TO_COUNTRY_ACT=4;
        public static final int MOVE_TO_CITY_ACT=5;
        public static final int COUNTRY_ADAPTER=6;
        public static final int MOVE_TO_MAP_ACT=7;
        public static final int MOVE_TO_SPECIALIZATION_ACT=8;
        public static final int SPECIALIZATION_ADAPTER=9;

    }
    public static class SharedPrefConstants{
        public static final String SHARED_PREF_NAME="TADAWAY_SHARED_PREF";
    }
    public static class IntentConstants{
        public static final String CLIENT_REQUEST="CLIENTREGISTERREQUEST";
        public static final String COUNTRY_DATA="COUNTRY";
        public static final String CITY_DATA="CITY";
        public static final String COMPANYITEMINFO="COMPANYINFO";
        public static final String CONTAINER_RESERVATION="RESERVATION";
        public static final String COMPANY_REQUEST="REQUEST";
        public static final String ADDRESS_REQUEST="ADDRESSREQUEST";
        public static final String LONGITUDE_REQUEST="longitude";
        public static final String LATITUDE_REQUEST="latitude";
        public static final String SPECIALIZATION_DATA="SPECIALIZATION";
        public static final String TYPE="type";
        public static final String REQUEST_ITEM_DATA="ITEMREQUEST";
        public static final int REQUEST_CODE_COUNTRY=111;
        public static final int REQUEST_CODE_CITY=222;
        public static final int REQUEST_CODE_MAP=333;
        public static final int REQUEST_CODE_SPECIALIZATION=444;


    }


}
