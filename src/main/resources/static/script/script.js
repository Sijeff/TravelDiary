function SQLinfon() {
    inputComment.innerHTML = `PostMapping / INSERT INTO information:
    Title: ${inputTitle.value}
    City: ${inputCity.value}
    Country: ${inputCountry.value}
    Start date: ${inputStartDate.value}
    End date: ${inputEndDate.value}
    Text: ${inputComment.value}
    lat: ${inputLat.value}
    lng: ${inputLng.value}
    `;
}

var inputTitle = document.getElementById("title");
var inputCity = document.getElementById("locality");
var inputCountry = document.getElementById("country");
var inputComment = document.getElementById("comment");
var inputStartDate = document.getElementById("startdate");
var inputEndDate = document.getElementById("enddate");

var inputLat = document.getElementById("lat");
var inputLng = document.getElementById("lng");

var submitBtn = document.getElementById("SubmitKnappen");
submitBtn.addEventListener("click", SQLinfon);

var latBtn = document.getElementById("late");
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
    country: 'long_name'
};

var options = {
    types: ['(cities)']
};

function initAutocomplete() {
    autocomplete = new google.maps.places.Autocomplete((document.getElementById('location')), options);
    autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
    var place = autocomplete.getPlace();

    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
    document.getElementById("lat").value = '';
    document.getElementById("lat").disabled = false;
    document.getElementById("lat").value = place.geometry.location.lat();

    document.getElementById("lng").value = '';
    document.getElementById("lng").disabled = false;
    document.getElementById("lng").value = place.geometry.location.lng();

}