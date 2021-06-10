package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes;

public class HIX_HOME {


    // if you want to fetch data Online (from server) set : true.
    // if you want fatch data Offline (from asset) set    : false.
    public static boolean ONLINE_OFFLINE = true ;

    //Attention : after apply mode offline please active this choise to crypt your data
    // why Im using this : this methode it best choise to prtotect your code from reverse engine.
    public static boolean useCRYPTDATA = false ;

    // if you want to use ADS online set true.
    // if you want to use ADS offline set false and complet your ids
    public static boolean useONLINE_ADNETWORK = true ;
//*********************************************************************************
    // Your link server json her after choose online mode.
    //https://ia601403.us.archive.org/32/items/dataON/dataON.json
    public static String JSONLINK_on = "";

    // your link privacy policy her :
    public static String PrivacyPolicy = "";
    // your develper google play store name :
    public static String DeveloperName = "";

    //put your OneSignal App Id here
    public static final String OneSignalAppId = "";

//*********************************************************************************


    // your name json file in assets :
    public static String JSONDATA_off = "dataOFF.json";

    // after apply mode ADS offline ! choose your ntewrok ads :
    // if you want to use AdMob enter : admob
    // if you want to use Facebook enter : facebook
    // if you want to use Mix Ads (Random ads between Facebook & admob) enter : mix
    public static String NetworkDefault = "admob";

    // AdMob Network Ads :
    public static String AdMob_Id = "";  //AdMob Id For GDPR.
    public static String Banner_AdMob = "";  //AdMob Banner Id
    public static String Interstitial_AdMob = ""; //AdMob Interstitial Id
    public static String NativeAds_AdMob = ""; //AdMob Native Id

    // Ids For Facebook Networks Ad :
    public static String Banner_AdUnit_Facebook = "" ;
    public static String Interstitial_AdUnit_Facebook = "" ;
    public static String NativeAds_Facebook = "";


    // true to show native ad in list iems :
    public static boolean showNativeAdInList = true ;
    // true to show native ad in Content (Tips detail) :
    public static boolean showNativeAdInContent = true ;

    // force loading list to load native same time:
    public static boolean forceNative = true ;


    // your name folder in assets to load image preview on List.
    public static String FOLDER_PREVIEW  = "preview";
    // your name font in assets :
    public static String ApplicationFont = "SF_Medium.otf";



    // If you want to use Music inside App set : true.
    public static boolean useMusic = true ;
    // If you want to use Particles stars effect set : true.
    public static boolean useParticles = true ;

    // if you want to show button next and preview in content set true.
    public static boolean ShowNEXT_PREVIEW = false ;
    //When show Next Button If you want to show Interstitial enter true :
    public static boolean ShowAdsWhenClickNext = false ;
    // next & preview counter number of ads and show it :
    public static int NumberofAdsCounter = 2 ;

    //Toast Text :
    public static String open_link_failed = "Failed to open the link";
    public static String open_package_failed = "The Game not installed";
   // Content Title
    public static String tips = "Part";

    //**********************************************************************************************


    // This is Values From Json File : Don't change This :
    public static String JsObject = "GuideData" ;
    public static String JsArrayList = "items" ;
    public static String JsArray = "content" ;
    //For Guide Text data :
    public static String JsTitle = "title";
    public static String JsPreview = "image";
    public static String Jscontent = "text";
    public static String Jsimage = "image_link";
    public static String Jsorder = "ordered";
    public static String JStext_size ="text_size";
    public static String Jscolor = "color";
    public static String Jsstyle = "style";
    public static String Jsgravity = "gravity";
    public static String Jsleft = "left";
    public static String JsisLink = "isLink";
    public static String JssetLinks = "setLink";
    public static String JslinkTitle = "link_title";
    public static String JssetNativeAds = "isNative";
    public static String assetsPath = "file:///android_asset/";

    //For Ads data :
    public static String JsArrayAds = "AdsController" ;
    public static String JsObjectNetworkAds = "NetworkAds" ;
    public static String JsObjectAdID = "AdMobId" ;
    public static String JsObjectAdBanner = "BannerAdmob" ;
    public static String JsObjectAdInterstitial = "InterstitialAdmob" ;
    public static String JsObjectAdNative = "NativeAdmob" ;
    public static String JsObjectFbBanner = "BannerFacebook" ;
    public static String JsObjectFbInterstitial = "InterstitialFacebook" ;
    public static String JsObjectFbNative = "NativeFacebook" ;

    // Don't change This :
    public static class Tags{

        public static String MARKET ="market://details?id=";
        public static String GGAPPS ="http://play.google.com/store/apps/details?id=";
        public static String GGDEV ="https://play.google.com/store/apps/developer?id=";
        public static String HTTP ="http";
        public static String WWW ="www";
        public static String True ="true";
        public static String DONE ="done";
        public static String FAILED ="failed";
        public static String NotAvailableMessage ="There is no app available for this task";
        public static String AdMob = "admob";
        public static String Facebook = "facebook";
        public static String Mix = "mix";
    }


}
