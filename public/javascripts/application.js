$(document).ready(function(){
  jQuery.fn.outerHTML = function() {
    return jQuery('<div />').append(this.eq(0).clone()).html();
  };
  
  
   deliveryTemplate = $("#deliveries").html();
   dateFormat = 'MMMM dd, yyyy';
   flowerPrice = 50;
   maxOrderSize = 7;
   success = false; //variable to make selenium test assertion easier. It becomes true when an order is placed.
   
   
   //insta romeao object. Contains functions and attributes for processing orders
   instaromeo_order = {
       gfName: "",
       total: 0,
       deliveries: [],
       //adds a new delivery to the flower order
       addDelivery: function(delivery) {
         var id = 'delivery_' + instaromeo_order.deliveries.length;
         instaromeo_order.deliveries.push(delivery);
         
         var deliveryHTML = $(deliveryTemplate);
         deliveryHTML.find(".icon img").attr("src", "/public/images/" + delivery.type + ".png");
         deliveryHTML.find(".date-text").text(delivery.date.toString(dateFormat));
         deliveryHTML.attr('id', id);
         x = deliveryHTML
         $("#deliveries").append(deliveryHTML.outerHTML());
         
         $(".total").html(this.total += flowerPrice);
         
         // update the deliveryHTML with the actual node in the dom.
         deliveryHTML = $("#" + id);
         deliveryHTML.fadeIn();
         if (delivery.type == "everyday" || delivery.type == "birthday" || delivery.type == "anniversary") {
           var datepicker = $("#" + id + " .datepicker");
           datepicker.show();
           // the date picker has to be initialized after the node is in the dom
           initDatePicker(datepicker, function(target, newDate)
           {
             target.parent().find(".date-text").text(newDate.toString(dateFormat));
             delivery.date = newDate;
           });
         }
 
       },
       //removes a delivery from the flower order
       removeDelivery: function(id) {
         if (instaromeo_order.deliveries.length >= 2) {
           instaromeo_order.deliveries[id] = null;
           $("#delivery_" + id).fadeOut();
           $("#delivery_" + id).remove();
           $(".total").html(this.total -= flowerPrice);
         } else {
           alert("You must have at least one date picked.");
         }
       },
       //adds a new delivery by parsing a date picker element in the DOM
       addDeliveryFromDatePicker: function(delivery_type, datepicker_id){
         var dateStr = $("#" + datepicker_id).val();
         if (dateStr) {
           try {
             var delivery = {
               type: delivery_type,
               date: Date.parse($("#" + datepicker_id).val())
             };
             this.addDelivery(delivery);  
           } catch(err) {
             
           }
 
         }

       },
       //generates a random delivery within the next year and adds it to the order
       addRandomDelivery: function(){
           var date = Date.parse(Math.ceil(Math.random()*12) + "/" + Math.ceil(Math.random()*29));
           if(date.compareTo(Date.parse("Today")) < 0){
               date.addYears(1);
           }
           var delivery = {
               type: "everyday",
               date: date 
           };
           this.addDelivery(delivery); 
       }
   };
   
   //bind the add delivery button
   $('#add-delivery').click(function(){
     var delivery = {
       type: 'everyday',
       date: new Date()
     };
     instaromeo_order.addDelivery(delivery);
     if (instaromeo_order.deliveries.length >= maxOrderSize) {
       $(this).hide();
       $("#max-orders").show();
     }
   });
   
   //creates the slider used for the single page app navigation
   slider = $('#slider1').bxSlider({
     mode: 'fade',
     controls: false
   });
   
   //bind function to keypress when gdname text box has focus
   $('#gfname').keypress(function(e){
       var code = (e.keyCode ? e.keyCode : e.which);
        if(code == 13) { //If Enter Key Code 
            $(this).next().focus(); //DO NOT SUBMIT FORM!
            $('#nameNext').trigger('click');
            var form = $(this).parents(".wiz-slide").find("form");
            //check if form is valid, if so advance slider
            if(form.length > 0 && !form.validate().form()) {
                return false;
            }
            $('#nameNext.btn').trigger('click');
            return false;
        }
   });
   
    //bind function for name next button
    $('#nameNext').click(function() {
      instaromeo_order.gfName = $("#gfname").val();
      $(".recipientName").val(instaromeo_order.gfName);
      $("#nameForm form").validate();
    });
    
    //bind function for next button
    $('.next').click(function(){
      var form = $(this).parents(".wiz-slide").find("form");
      if(form.length > 0 && !form.validate().form()) {
        return false;
      }
      slider.goToNextSlide();
      $(".inactive").first().removeClass("inactive");
      return false;
    });

     //bind function for skip button
     $('.skip').click(function(){
       slider.goToNextSlide();
       $(".inactive").first().removeClass("inactive");
       return false;
     });
     
    //bind function for anniversary next button
     $("#annivNext").click(function(){
         instaromeo_order.addDeliveryFromDatePicker("anniversary", "anniversary");
     });
     
     //bind function for birthday next button
     $("#birthdayNext").click(function(){
         instaromeo_order.addDeliveryFromDatePicker("birthday", "birthday");
     });
     
     //bind function for yes button for random delivery question
     $("#randomNextYes").click(function(){
         instaromeo_order.addRandomDelivery();
     });
     
     //bind function to purchase button
     $("#purchase").click(function() {
       var form = $(this).parents(".wiz-slide").find("form");
       if(form.length > 0 && !form.validate().form()) {
         return false;
       }
       $("#billingForm .buttons").hide();
       $("#purchaseLoading").show();
       var data = $("#senderInfo, #recipientInfo").serialize();
       data += "&deliveries=" + JSON.stringify(instaromeo_order.deliveries);
       $.post("/orders", data).success(function() {
         slider.goToNextSlide();
         success = true;
       }).error(function() {
         // something went wrong, but let's pretend it didn't :p
         slider.goToNextSlide();
       });
     });
     
     //The default configuration for all datepickers
     function initDatePicker(element, onChange) {
       element.glDatePicker(
          {
             cssName: "android",
             startDate: new Date(Date.parse("Today")),
             endDate: new Date(Date.parse("Yesterday").addYears(1)),
             allowOld: false,
             position: "fixed",
             onChange: onChange
           });
     }
     
     $(".modifyOrder").click(function() {
       slider.goToSlide(4);
     });
     $(".modifyAddress").click(function() {
       slider.goToSlide(5);
     });
     $(".datepicker").each(function(){
       initDatePicker($(this), function(target, newDate)
       {
         target.val(newDate.toString(dateFormat));
       });
     });
     
     // initialize common dates
     instaromeo_order.addDelivery({
         type: "valentines", 
         date: Date.parse("February 14 2012")
     });

       $(".remove").live('click',function(ev){
         
         ev.preventDefault();
         instaromeo_order.removeDelivery($(this).parent().attr("id").split("_")[1]);
         //instaromeo_order.deliveries = instaromeo_order.deliveries.filter(function(e){return e!=null});
         $("#add-delivery").show();
         $("#max-orders").hide();
       });
       
       $(".datepicker").live('keyup',function(){
           if (Date.parse($(this).val()) !== null)
           {
             var newDate = Date.parse($(this).val());
             if (newDate.getFullYear() < Date.parse("today").getFullYear()){
                 newDate.set({year: Date.parse("today").getFullYear()});
             }
             if(newDate.compareTo(Date.parse("Today")) < 0){
                   newDate.addYears(1);
             }
             $(this).glDatePicker("setSelectedDate", newDate);
             $(this).glDatePicker("gotoSelectedDate"); 
           }
 
       });

       var typewatch = (function(){
         var timer = 0;
         return function(callback, ms){
           clearTimeout (timer);
           timer = setTimeout(callback, ms);
         }  
         })();
        
         
     
 });