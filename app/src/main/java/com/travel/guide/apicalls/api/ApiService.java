package com.travel.guide.apicalls.api;

import com.travel.guide.apicalls.model.AddWishListResBean;
import com.travel.guide.apicalls.model.AmenitiesResBean;
import com.travel.guide.apicalls.model.AreaListResBean;
import com.travel.guide.apicalls.model.BannerResBean;
import com.travel.guide.apicalls.model.BookingDetailsResBean;
import com.travel.guide.apicalls.model.CityResBean;
import com.travel.guide.apicalls.model.CouponAppliedResBean;
import com.travel.guide.apicalls.model.DistanceRangeResBean;
import com.travel.guide.apicalls.model.EditProfileResBean;
import com.travel.guide.apicalls.model.ExploreMoreResBean;
import com.travel.guide.apicalls.model.FaqResBean;
import com.travel.guide.apicalls.model.HomeDataResBean;
import com.travel.guide.apicalls.model.InstituteListResBean;
import com.travel.guide.apicalls.model.LoginResBean;
import com.travel.guide.apicalls.model.MessDetailsResBean;
import com.travel.guide.apicalls.model.MyBookingsResBean;
import com.travel.guide.apicalls.model.NotificationResBean;
import com.travel.guide.apicalls.model.OfferResBean;
import com.travel.guide.apicalls.model.OfflinePaynowResBean;
import com.travel.guide.apicalls.model.PopularPropertyResBean;
import com.travel.guide.apicalls.model.PriceRangeResBean;
import com.travel.guide.apicalls.model.ProfileResBean;
import com.travel.guide.apicalls.model.PropertyDetailsResBean;
import com.travel.guide.apicalls.model.PropertyTypeResBean;
import com.travel.guide.apicalls.model.RecentlyVisitedResBean;
import com.travel.guide.apicalls.model.RecommendedResBean;
import com.travel.guide.apicalls.model.RegisterResBean;
import com.travel.guide.apicalls.model.RequestCallbackResBean;
import com.travel.guide.apicalls.model.RoomBookingResBean;
import com.travel.guide.apicalls.model.RoomDetailsResBean;
import com.travel.guide.apicalls.model.SearchByInsResBean;
import com.travel.guide.apicalls.model.SearchByLPropertyResBean;
import com.travel.guide.apicalls.model.SearchPropertyResBean;
import com.travel.guide.apicalls.model.ServiceableCityResBean;
import com.travel.guide.apicalls.model.StateResBean;
import com.travel.guide.apicalls.model.VerifyOtpResBean;
import com.travel.guide.apicalls.model.WalletResBean;
import com.travel.guide.apicalls.model.WishlistResBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    /*Login Api */
   /* @FormUrlEncoded
    @POST("send-login-otp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<LoginResBean> login(@Field("mobile") String mobile,
                             @Field("role_id") String role_id,
                             @Field("device_key") String device_key);

    *//*Verify OTP Api *//*
    @FormUrlEncoded
    @POST("send-verify-otp")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<VerifyOtpResBean> verifyOTP(@Field("mobile") String mobile,
                                     @Field("otp") String otp);
*/
    /*Add User*/
   /* @Multipart
    @POST("vendor/register")
    Call<RegisterResBean> registration(@Part("name") RequestBody user_name,
                                    @Part("gender") RequestBody gender,
                                    @Part("mobile") RequestBody mobile,
                                    @Part("email") RequestBody email,
                                    @Part("business_organization") RequestBody business_organization,
                                    @Part("address") RequestBody address,
                                    @Part("pincode") RequestBody pincode,
                                    @Part MultipartBody.Part user_image,
                                    @Part("device_id") RequestBody device_id);*/





    /*State Api */
    @POST("state")
    Call<StateResBean> getStates();

    /*City api*/
    @FormUrlEncoded
    @POST("city")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<CityResBean> getCities(@Field("state_id") String state_id);

    /*ServiceableCityList Api*/
    @POST(ApiConstants.POPULAR_PROPERTY_URL)
    @Headers({"Accept: application/json"})
    Call<PopularPropertyResBean> getPopularPropertiesList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Register api*/
    @FormUrlEncoded
    @POST(ApiConstants.REGISTER_URL)
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<RegisterResBean> registerUser(@Field("name") String name,
                                       @Field("mobile") String mobile,
                                       @Field("email") String email,
                                       @Field("referal_code") String referralCode,
                                       @Field("city") String city);

    /*Login api*/
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_URL)
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<LoginResBean> loginUser(@Field("email_mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiConstants.VERIFY_URL)
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<VerifyOtpResBean> verifyOTP(@Field("mobile") String mobile,
                                     @Field("device_key") String deviceKey,
                                     @Field("otp") String otp);

    /*PopularPropertiesList Api*/
    @POST(ApiConstants.SERVICEABLE_CITY_LIST_URL)
    @Headers({"Accept: application/json"})
    Call<ServiceableCityResBean> getServiceableCityList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*PropertyDetails Api*/
    @FormUrlEncoded
    @POST(ApiConstants.PROPERTY_DETAILS_URL)
    @Headers({"Accept: application/json"})
    Call<PropertyDetailsResBean> getPropertyDetails(@Field(ApiConstants.PROPERTY_ID) String property_id,
                                                    @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*RoomDetails Api*/
    @FormUrlEncoded
    @POST(ApiConstants.ROOM_DETAILS_URL)
    @Headers({"Accept: application/json"})
    Call<RoomDetailsResBean> getRoomDetails(@Field(ApiConstants.PROPERTY_ID) String property_id,
                                            @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*MessDetails Api*/
    @FormUrlEncoded
    @POST(ApiConstants.MESS_DETAILS_URL)
    @Headers({"Accept: application/json"})
    Call<MessDetailsResBean> getMessDetails(@Field(ApiConstants.PROPERTY_ID) String property_id,
                                            @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*RoomBooking Api*/
    @FormUrlEncoded
    @POST(ApiConstants.ROOM_BOOKING_URL)
    @Headers({"Accept: application/json"})
    Call<RoomBookingResBean> saveRoomBooking(@Field(ApiConstants.PROPERTY_ID) String property_id,
                                             @Field(ApiConstants.USER_NAME) String user_name,
                                             @Field(ApiConstants.BOOKING_DATE) String booking_date,
                                             @Field(ApiConstants.MOBILE) String mobile,
                                             @Field(ApiConstants.ROOM_ID) String room_id,
                                             @Field(ApiConstants.ROOM_TYPE_ID) String room_type_id,
                                             @Field(ApiConstants.PAYMENT_TYPE) String booking_type,
                                             @Field(ApiConstants.WALLET_AMOUNT) String wallet_amount,
                                             @Field(ApiConstants.COUNPON_CODE) String coupon_code,
                                             @Field(ApiConstants.COUPON_AMOUNT) String coupon_amount,
                                             @Field(ApiConstants.TRANSACTION_ID) String transaction_id,
                                             @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Banner Api*/
    @FormUrlEncoded
    @POST(ApiConstants.BANNER_URL)
    @Headers({"Accept: application/json"})
    Call<BannerResBean> getBanners(@Field(ApiConstants.TYPE) String type,
                                   @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*RecommendedPropertiesList Api*/
    @FormUrlEncoded
    @POST(ApiConstants.RECOMMENDED_URL)
    @Headers({"Accept: application/json"})
    Call<RecommendedResBean> getRecommendedPropertiesList(@Field(ApiConstants.BUSINESSCAT_ID) String propertyTypeId,
                                                          @Field(ApiConstants.PRICE_RANGE_ID) String priceRangeId,
                                                          @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*DistanceRange Api*/
    @POST(ApiConstants.DISTANCE_RANGE_URL)
    @Headers({"Accept: application/json"})
    Call<DistanceRangeResBean> getDistanceRangeList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Amenities Api*/
    @POST(ApiConstants.AMENITIES_URL)
    @Headers({"Accept: application/json"})
    Call<AmenitiesResBean> getAmenities(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Property Api*/
    @POST(ApiConstants.PROPERTY_TYPE_URL)
    @Headers({"Accept: application/json"})
    Call<PropertyTypeResBean> getPropertyType(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*SearchByLocation Api*/
    @FormUrlEncoded
    @POST(ApiConstants.SEARCH_BY_LOCATION_URL)
    @Headers({"Accept: application/json"})
    Call<SearchByLPropertyResBean> searchByLocation(@Field(ApiConstants.CITY_ID) String cityId,
                                                    @Field(ApiConstants.AREA_ID) String areaId,
                                                    @Field(ApiConstants.BUSINESSCAT_ID) String propertyId,
                                                    @Field(ApiConstants.RANGE_ID) String rangeId,
                                                    @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*GetIntituteList Api*/
    @FormUrlEncoded
    @POST(ApiConstants.INSTITUTE_LIST_URL)
    @Headers({"Accept: application/json"})
    Call<InstituteListResBean> getIntituteList(@Field(ApiConstants.CITY_ID) String cityId,
                                               @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*SearchByInstitute Api*/
    @FormUrlEncoded
    @POST(ApiConstants.SEARCH_BY_INSTITUTE_URL)
    @Headers({"Accept: application/json"})
    Call<SearchByInsResBean> searchByInstitute(@Field(ApiConstants.BUSINESSCAT_ID) String propertyId,
                                               @Field(ApiConstants.RANGE_ID) String rangeId,
                                               @Field(ApiConstants.CITY_ID) String cityId,
                                               @Field(ApiConstants.INSTITUTE_ID) String instituteId,
                                               @Field(ApiConstants.BRANCH_ID) String branchId,
                                               @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*MyBookings Api*/
    @POST(ApiConstants.BOOKING_LIST_URL)
    @Headers({"Accept: application/json"})
    Call<MyBookingsResBean> getBookingList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Wallet Api*/
    @FormUrlEncoded
    @POST(ApiConstants.WALLET_URL)
    @Headers({"Accept: application/json"})
    Call<WalletResBean> getWalletHistory(@Field(ApiConstants.TYPE) String type,
                                         @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*FAQ Api*/
    @POST(ApiConstants.FAQ_URL)
    @Headers({"Accept: application/json"})
    Call<FaqResBean> getFaqList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*RecentlyVisited Api*/
    @POST(ApiConstants.RECENTLY_VISITED_URL)
    @Headers({"Accept: application/json"})
    Call<RecentlyVisitedResBean> getRecentlyVisitedList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Notification Api*/
    @POST(ApiConstants.NOTIFICATION_LIST_URL)
    @Headers({"Accept: application/json"})
    Call<NotificationResBean> getNotificationList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Offer Api*/
    @POST(ApiConstants.OFFER_URL)
    @Headers({"Accept: application/json"})
    Call<OfferResBean> getOfferList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*AddWishList Api*/
    @FormUrlEncoded
    @POST(ApiConstants.ADD_WISHLIST_URL)
    @Headers({"Accept: application/json"})
    Call<AddWishListResBean> addWishList(@Field(ApiConstants.PROPERTY_ID) String property_id,
                                         @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*GetWishList Api*/
    @POST(ApiConstants.GET_WISHLIST_URL)
    @Headers({"Accept: application/json"})
    Call<WishlistResBean> getMyWishlist(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*PriceRange Api*/
    @POST(ApiConstants.PRICE_RANGE_URL)
    @Headers({"Accept: application/json"})
    Call<PriceRangeResBean> getPriceRangeList(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*UpdateProfile Api*/
    @Multipart
    @POST(ApiConstants.UPDATE_PROFILE_URL)
    @Headers({"Accept: application/json"})
    Call<EditProfileResBean> editProfile(@Part(ApiConstants.NAME) RequestBody name,
                                         @Part(ApiConstants.EMAIL) RequestBody email,
                                         @Part(ApiConstants.MOBILE) RequestBody mobile,
                                         @Part(ApiConstants.DAY) RequestBody description,
                                         @Part(ApiConstants.MONTH) RequestBody status,
                                         @Part(ApiConstants.YEAR) RequestBody min_price,
                                         @Part MultipartBody.Part user_image,
                                         @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*GetProfile Api*/
    @POST(ApiConstants.GET_PROFILE_URL)
    @Headers({"Accept: application/json"})
    Call<ProfileResBean> getProfileInfo(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*GetCouponList Api*/
    @POST(ApiConstants.GET_COUPON_URL)
    @Headers({"Accept: application/json"})
    Call<OfferResBean> getCouponlist(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Coupon applied Api*/
    @FormUrlEncoded
    @POST(ApiConstants.COUPON_APPLIED_URL)
    @Headers({"Accept: application/json"})
    Call<CouponAppliedResBean> couponApplied(@Field(ApiConstants.COUNPON_CODE) String couponCode,
                                             @Field(ApiConstants.AMOUNT) String amount,
                                             @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Request Callback Api*/
    @FormUrlEncoded
    @POST(ApiConstants.REQUEST_CALLBACK_URL)
    @Headers({"Accept: application/json"})
    Call<RequestCallbackResBean> sendRequestCallback(@Field(ApiConstants.MOBILE) String mobile,
                                                     @Field(ApiConstants.CITY_ID) String cityId,
                                                     @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Explore More Api*/
    @POST(ApiConstants.EXPLORE_NOW)
    @Headers({"Accept: application/json"})
    Call<ExploreMoreResBean> getExploreBanners(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*Booking Details Api*/
    @FormUrlEncoded
    @POST(ApiConstants.BOOKING_DETAILS)
    @Headers({"Accept: application/json"})
    Call<BookingDetailsResBean> getBookingDetails(@Field(ApiConstants.BOOKING_ID) String bookingId,
                                                  @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*OfflineBookingPay Api*/
    @FormUrlEncoded
    @POST(ApiConstants.OFFLINE_PAYNOW)
    @Headers({"Accept: application/json"})
    Call<OfflinePaynowResBean> offlinePayNow(@Field(ApiConstants.BOOKING_ID) String bookingId,
                                             @Field(ApiConstants.TRANSACTION_ID) String transactionId,
                                             @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*AreaList Api*/
    @FormUrlEncoded
    @POST(ApiConstants.AREA_LIST)
    @Headers({"Accept: application/json"})
    Call<AreaListResBean> getAreaList(@Field(ApiConstants.CITY_ID) String cityId,
                                      @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*SearchPropertyList Api*/
    @FormUrlEncoded
    @POST(ApiConstants.SEARCH_PROPERTY_LIST)
    @Headers({"Accept: application/json"})
    Call<SearchPropertyResBean> getSearchPropertyList(@Field(ApiConstants.SEARCH) String type,
                                                      @Header(ApiConstants.AUTHORIZATION) String accessToken);

    /*DistanceRange Api*/
    @POST(ApiConstants.HOME_DATA_API)
    @Headers({"Accept: application/json"})
    Call<HomeDataResBean> getHomeData(@Header(ApiConstants.AUTHORIZATION) String accessToken);

    //AAAA9Ip0ckA:APA91bGduUopfvRG85JDvniU9g6Yl1_ElM50C1yaDVNMacNTUjB-Oz2XnRtDbbHt2rJT90f2WmEGBIQhCy0ptMLthb8GpPBmjQG5inHJsFwPghb_9ud1rsfgFlsMaricYHhykdfzKfop
    //FOR Firebase Chat notification
    /*@Headers({"Content-Type:application/json",
            "Authorization:key=AAAA2wFIdD8:APA91bELVy3mKEF5uPKbjI9AtiTT0YGu7QYV46UnPoggGR2mE3Xmr9bSojYpI6rtNtsPKoDx24hjOLgRpDUb-rx_29kFE8paBQ_BcVaEMdc_8JxRolP7vV_SiMmaGMo4O-VEADso_l5Z"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);*/



}



