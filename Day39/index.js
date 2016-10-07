angular.module("AppMod", ["ngRoute"])
	.controller("AppCtrl", ['$http', '$routeParams', function($http, $routeParams) {
		var self = this;

		self.id = $routeParams.stuID;
		
		$http.get('http://localhost:8080/student/'+self.id)
			.then(function(resp){
				self.student = resp.data;
				console.log(self.first_name)
			},function(err) {

			});
		
		$http.get('http://localhost:8080/student')
			.then(function(resp){
				self.students = resp.data;
				
				self.add=(function() 
	        	{
					//console.log("pressed add button");
					
					var first_name = document.getElementById("addFirst").value;
	            	var last_name = document.getElementById("addLast").value;
	            	var sat = document.getElementById("addSAT").value;
	            	var gpa = document.getElementById("addGPA").value;
					//console.log(first_name + last_name + sat + gpa);
					
	        		$.ajax(
					{
	        			url:"http://localhost:8080/add/",
	           			type:'POST',
	           			dataType: 'json',
	           			data:
	           			{
		           			"first": first_name,
		           			"last": last_name,
		           			"sat": sat,
		           			"gpa": gpa
	           			}
	        		}).then(function(result) 
	        		{
	        			console.log("add added");
	        		});
	        				
	        		document.getElementById("addFirst").value="";
	            	document.getElementById("addLast").value="";
	            	document.getElementById("addSAT").value="";
	            	document.getElementById("addGPA").value="";
	        	
	        	})
				
		        self.delete = (function(stuID) 
		        {	
		        	$.ajax({
		        	url:"http://localhost:8080/delete/" + stuID,
		           	type:'DELETE'
		        	}).then(function(result) 
		        	{
		        		//console.log("testing delete");
		        	});			
					
		        })//end of delete
		        			
				self.edit = (function(stuID) 
		        {
					//console.log(document.getElementById("sat"+stuID).value);
		        		 		
		            if(document.getElementById("sat"+stuID).disabled == true)
		            {
		            	document.getElementById("sat"+stuID).disabled = false;
		            	document.getElementById("gpa"+stuID).disabled = false;
		            	//document.getElementById(button).value = "submit";
		            }
		            else
		            {
						document.getElementById("sat"+stuID).disabled = true;
		            	document.getElementById("gpa"+stuID).disabled = true;
		            	//document.getElementById(button).value = "edit";
		            			
		            	var stu_sat = document.getElementById("sat"+stuID).value;
						var stu_gpa = document.getElementById("gpa"+stuID).value;
						//console.log(stu_sat);
						//console.log(stu_gpa);
									
		            	$.ajax(
						{
							url:"http://localhost:8080/update/" + stuID,
				       		type:'POST',
				        	dataType: 'json',
				        	data:
				        	{
					      		"sat": stu_sat,
					      		"gpa": stu_gpa
				          	}
				        }).then(function(result) 
				        {
							console.log("testing delete");
				        });
						
		            }
					
		        })//end of edit function
			},function(err) {

			});
			
			
		self.refresh = function()
		{
			for(var i of self.students)
				i.vis = true;
		};
		
		self.hide = function(stuID){
			self.students[stuID].vis = false;
		};

	}])
	.config(['$routeProvider', function($routeProvider) {

		$routeProvider
		.when('/', {
			templateUrl: 'views/home.view.html'

		}).when('/student', {
			templateUrl: 'views/student.view.html',
			controller: 'AppCtrl',
			controllerAs: 'ctrl'
			
		}).when('/studentlist', {
		templateUrl: 'views/StudentsList.html',
		controller: 'AppCtrl',
		controllerAs: 'ctrl'

		}).when('/about', {
			templateUrl: 'views/about.view.html'
		
		}).when('/student/:stuID', {
			templateUrl: 'views/Astudent.html',
			controller: 'AppCtrl',
			controllerAs: 'ctrl'

		})
		.otherwise({redirectTo: '/'});

	}]); // end config