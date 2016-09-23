var token = getSlackToken();
var chan;


$(document).ready(function(){
	$("#message").val("");
	listPublicChannels();
	listPrivateChannels();
	
	$("#toggle").click(function() 
	{
		if(document.getElementById("toggle").value === "Show private channels")
		{
			document.getElementById("toggle").value = "Hide private channels";
			$("#PrivateChannels").toggle();
		}	
		else
		{
			document.getElementById("toggle").value = "Show private channels";
			$("#PrivateChannels").toggle();
		}
	})
	$("#post").click(function() 
	{
		var mess = $("#message").val();
		
		var msgData = 
		{
			"token": token,
			"channel": chan,
			"text": mess,
			"username": "TesterofTestBots"
		}
		
			$.ajax("https://slack.com/api/chat.postMessage", {
			data: msgData,
			method: "POST"
			}).then(function(result) 
			{
				//console.log(result);
			});
			
			$("#message").val("");
			
		})
		
	
});




function listPublicChannels()
{
	$.ajax("https://slack.com/api/channels.list", {
		data: {"token": token},
		method: "GET"
		}).then(function(result) 
		{
			for(var i = 0; i < result.channels.length;i++)
			{
				var name = result.channels[i].name;
				var id = result.channels[i].id;
				//console.log(id);
				var item = $("<input type='radio' name='channel' value=" 
					+ id + ">" + name + "<br>");
				$("#PublicChannels").append( item );
				//console.log(result.channels[i].name);
			}
			
			$("input[type='radio']").click(function()
			{
				//console.log("got in public");
				chan = $("input[type='radio']:checked").val();
				//console.log("Chan:" + chan);
			});
		});
		
		
}

function listPrivateChannels()
{
	$.ajax("https://slack.com/api/groups.list", {
		data: {"token": token},
		method: "GET"
		}).then(function(result) 
		{
			for(var i = 0; i < result.groups.length;i++)
			{
				var name = result.groups[i].name;
				var id = result.groups[i].id;
				//console.log(id);
				var item = $("<input type='radio' name='channel' value=" 
					+ id + ">" + name + "<br>");
				$("#PrivateChannels").append( item );
				//console.log(result.groups[i].name);
			}
			
			$("input[type='radio']").click(function()
			{
				//console.log("got in private");
				chan = $("input[type='radio']:checked").val();
				//console.log("Chan:" + chan);
			});
		});
		$("#PrivateChannels").hide();
		
}


	



