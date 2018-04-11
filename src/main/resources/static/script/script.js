var submitBtn = document.getElementById("SubmitKnappen");
submitBtn.addEventListener("click", SQLinfon);

var inputTitle = document.getElementById("title");
var inputCity = document.getElementById("locality");
var inputCountry = document.getElementById("country");
var inputComment = document.getElementById("comment");
var inputStartDate = document.getElementById("startdate");
var inputEndDate = document.getElementById("enddate");

function SQLinfon() {
    inputComment.innerHTML = `PostMapping / INSERT INTO information:
    Title: ${inputTitle.value}
    City: ${inputCity.value}
    Country: ${inputCountry.value}
    Start date: ${inputStartDate.value}
    End date: ${inputEndDate.value}
    Text: ${inputComment.value}
    `;
}

var latBtn = document.getElementById("lat");
latBtn.addEventListener("click", lat);

function lat() {
    inputComment.innerHTML = 'We had a fantastic week';
    inputTitle.value = 'Fantastic week in Norway';
    inputStartDate.value = '2018-02-01';
    inputEndDate.value = '2018-02-09';
}


var placeSearch, autocomplete;
var componentForm = {
    locality: 'long_name',
    country: 'long_name',
};

var opt = {
    types: ['geocode']
};

var options = {
    bounds: defaultBounds,
    types: ['(cities)'],
};

function initAutocomplete() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */
        (document.getElementById('location')), options);

    autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();

    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    // Get each component of the address from the place details
    // and fill the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}