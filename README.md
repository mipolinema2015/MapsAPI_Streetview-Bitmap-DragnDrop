# MapsAPI_Streetview-Bitmap-DragnDrop
Maps and streetview apps with dragNdrop sticker image.

Activity 1 :
-Maps Fragment with marker getting new Object LatLng
-Streetview Fragment, able to get picture of various places according to Maps Fragment marker position
-Search bar places, using GooglePlacesAPI, returns LatLng and name of places

Activity 2 :
-Captured picture from streetview fragment is shown on new Bitmap Object using Picasso
-Using GoogleStreetview Javascript API instead of Android by parsing LatLng to raw url request
-Drag and drop another ImageView, which acts as Sticker (Instagram reference)

Activity 3 :
-Screencapt and crop activity 2 to get StreetView image + Sticker
-Share and/or save to external


