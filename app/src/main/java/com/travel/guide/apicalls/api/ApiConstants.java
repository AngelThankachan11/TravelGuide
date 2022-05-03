package com.travel.guide.apicalls.api;

public class ApiConstants {

    // API BASE URL
    public static final String BASE_URL = "https://androidhiker.com/travelguide/public/api/v1/";
    //public static final String BASE_URL = "http://apneedukan.com/meraroom/public/api/v1/";
    public static final String TEST_BASE_URL = "http://mazra3ti.com/api/";
    public static final String BASE_IMAGE_URL = "https://androidhiker.com/travelguide/public/";
    //public static final String BASE_IMAGE_URL = "http://apneedukan.com/meraroom/public";
    public static final String ABOUT_US_URL = "https://helpindiaonline.co.in/bhukish/about.html";
    //HEADER ACCESS TOKEN PARAMETER
    public static final String ACCESS_TOKEN = "accesstoken:123456";

    // API URL
    public static final String LOGIN_URL = "login";
    public static final String REGISTER_URL = "register";
    public static final String VERIFY_URL = "register/otp-verify";
    public static final String POPULAR_PROPERTY_URL = "user/service_property";
    public static final String PROPERTY_DETAILS_URL = "user/property-details";
    public static final String ROOM_DETAILS_URL = "user/room-details";
    public static final String MESS_DETAILS_URL = "user/mess-details";
    public static final String ROOM_BOOKING_URL = "user/save-booking";
    public static final String BANNER_URL = "user/banner";
    public static final String RECOMMENDED_URL = "user/recommend-property";
    public static final String DISTANCE_RANGE_URL = "user/distance";
    public static final String AMENITIES_URL = "user/amenities";
    public static final String PROPERTY_TYPE_URL = "user/business-category";
    public static final String SEARCH_BY_LOCATION_URL = "user/search-by-location";
    public static final String INSTITUTE_LIST_URL = "user/get-institute";
    public static final String SEARCH_BY_INSTITUTE_URL = "user/search-by-institute";
    public static final String SERVICEABLE_CITY_LIST_URL = "user/serviceable-areas";
    public static final String BOOKING_LIST_URL = "user/list-booking";
    public static final String WALLET_URL = "user/wallet";
    public static final String FAQ_URL = "user/faq";
    public static final String RECENTLY_VISITED_URL = "user/list-recently-viewed";
    public static final String NOTIFICATION_LIST_URL = "user/list-notification";
    public static final String OFFER_URL = "user/coupon";
    public static final String ADD_WISHLIST_URL = "user/add-wishlist";
    public static final String GET_WISHLIST_URL = "user/list-wishlist";
    public static final String PRICE_RANGE_URL = "user/price-range";
    public static final String UPDATE_PROFILE_URL = "user/update-profile";
    public static final String GET_PROFILE_URL = "user/get-profile";
    public static final String GET_COUPON_URL = "user/coupon";
    public static final String COUPON_APPLIED_URL = "user/coupon_applied";
    public static final String REQUEST_CALLBACK_URL = "user/request-callbacks";
    public static final String EXPLORE_NOW = "user/explore-now";
    public static final String BOOKING_DETAILS = "user/booking-details";
    public static final String OFFLINE_PAYNOW = "user/offline-paynow";
    public static final String AREA_LIST = "user/areas-list";
    public static final String SEARCH_PROPERTY_LIST = "user/property-search";
    public static final String HOME_DATA_API = "user/home-apis";

    // PARAMETER

    public static final String INSTITUTE_ID = "institute_id";
    public static final String BRANCH_ID = "branch_id";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String NAME = "name";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String PINCODE = "pincode";
    public static final String PIN_CODE = "pin_code";
    public static final String HOUSE_NO = "house_no";
    public static final String NEAR_BY = "near_by";
    public static final String ADDRESS_TYPE = "address_type";
    public static final String CITY_ID= "city_id";
    public static final String AREA_ID = "area_id";
    public static final String ADDRESS_LINE_1= "address_line_1";
    public static final String QUANTITY= "quantity";
    public static final String ADDRESS_ID= "address_id";
    public static final String ITEM_ID = "item_id";
    public static final String COMMENT = "comment";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String AUTHORIZATION = "Authorization";
    public static final String PROPERTY_ID = "property_id";
    public static final String USER_NAME = "user_name";
    public static final String BOOKING_DATE = "booking_date";
    public static final String ROOM_ID = "room_id";
    public static final String ROOM_TYPE_ID = "room_type_id";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String TYPE = "type";
    public static final String WALLET_AMOUNT = "wallet_amount";
    public static final String COUPON_AMOUNT = "coupon_amount";
    public static final String COUNPON_CODE = "coupon_code";
    public static final String BOOKING_ID = "booking_id";
    public static final String SEARCH = "search";
    public static final String DELIVERY_DATE = "delivery_date";
    public static final String DELIERY_TIME = "delivery_time";
    public static final String ORDER_ID = "order_id";
    public static final String STATUS = "status";
    public static final String AMOUNT = "amount";
    public static final String RANGE_ID = "range_id";
    public static final String BUSINESSCAT_ID = "businesscat_id";
    public static final String PRICE_RANGE_ID = "price_range_id";



    // API Tag
    public static final String LOGIN_TAG = "login";
    public static final String REGISTER_TAG = "register";
    public static final String VERIFY_TAG = "verify";
    public static final String CATEGORY_TAG = "category";
    public static final String SUB_CATEGORY_TAG = "sub_category";
    public static final String SUB_SUB_CATEGORY_TAG = "sub_sub_category";
    public static final String STORE_TAG = "store";
    public static final String ADDRES_LIST_TAG = "address_list";
    public static final String DELETE_ADDRES_TAG = "delete_address_list";
    public static final String ADD_CART_TAG = "add_to_cart";
    public static final String ADD_ADDRESS_TAG = "add_address";
    public static final String UPDATE_ADDRESS_TAG = "update_address";
    public static final String CATEGORY_WITH_PRODUCT_TAG = "category_with_product";
    public static final String CART_LIST_TAG = "cart_item_list";
    public static final String CART_INCREMENT_TAG = "cart_increment";
    public static final String BANNER_TAG = "banner_offer";
    public static final String COUPON_LIST_TAG = "coupon_list";
    public static final String PRODUCT_DETAILS_TAG = "product_details";
    public static final String DAY_SLOT_TAG = "day_slot";
    public static final String DELIVERY_TYPE_TAG = "delivery_type";
    public static final String TIME_SLOT_TAG = "time_slot";
    public static final String DELETE_CART_ITEM_TAG = "delete_cart_item";
    public static final String SAVE_ORDER_TAG = "save_order";
    public static final String NOTIFICATION_TAG = "notification";
    public static final String ORDER_DETAILS_TAG = "order_details";
    public static final String MY_ORDER_TAG = "my_order";
    public static final String ORDER_TRACK_TAG = "order_track";
    public static final String CANCEL_ORDER_TAG = "cancel_order";
    public static final String ORDER_FEEDBACK_TAG = "feedback_order";


}

