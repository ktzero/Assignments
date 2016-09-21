$(document).ready(function() {

	var count = Number( $("#counter").val() );
	//console.log(incVal);
	var text = document.getElementById("counter");
	
	$("#increment").click(function() {
		
		if(document.getElementById("random").checked == true )
		{var incVal = Math.floor(Math.random() * 10) + 1;
		}
		else
		{var incVal = Number($("#incValue").val() );}
		count+= incVal;
		checkFont(count);
		text.value = count;
	})
	
	$("#decrement").click(function() {
		if(document.getElementById("random").checked == true)
		{var incVal = Math.floor(Math.random() * 10) + 1;}
		else
		{var incVal = Number($("#incValue").val() );}
		count-= incVal;
		checkFont(count);
		text.value = count;
	})
	
	$("#reset").click(function() {
		count = 0;
		$("#counter").removeClass("italics");
		$("#counter").removeClass("red");
		$("#counter").removeClass("green");
		text.value = count;
	})


});

function checkFont(count)
{
	if (count > 8) {
      $("#counter").addClass("red");
    } else {
      $("#counter").removeClass("red");
    }
	if(count % 2 === 0 && count !== 0)
	{$("#counter").addClass("italics");}
	else{$("#counter").removeClass("italics");}
	
	var string = count.toString();
	if(string.indexOf("7") > -1) 
	{$("#counter").addClass("green");}
	else{$("#counter").removeClass("green");}
	
}