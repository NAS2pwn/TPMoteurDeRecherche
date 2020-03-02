<?php
	class NavBarView{
		public function openList(){
			?>
			<nav class="mb-4 p-0 navbar navbar-expand-lg navbar-light bg-light secondary-color lighten-1 fixed-top border-bottom">
             
               <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#sw-nav" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
               	<span class="navbar-toggler-icon"></span>
  				</button>
              <div id="sw-nav" class="collapse navbar-collapse hidden-md-up">
                <ul class="navbar-nav mx-auto d-flex justify-content-center col-10">

			<?php
		}
		public function firstList(){
			?>		
					<li class="nav-item">
	                    <a id="message" class="nav-link waves-effect waves-light">
	                      <i class="fas fa-envelope"></i>
	                    </a>
		             </li>
		             <li class="form-inline input_msg_write row col-4 ">
					  <input id="search-bar" class="mr-2 col-10 border-bottom visible-lg " type="text" placeholder="   Fais une recherche poto"
					    aria-label="Search">
					    <!--<span class="visible-phone hidden-tablet hidden-desktop">myIconLabel</span>-->
					  <i id="search-btn" class="fas fa-search" aria-hidden="true"></i>
		            </li>
				</ul>
			<?php
		}

		public function closeList(){
			?>
              </div>
            </nav>
			<?php
		}

		public function closeList2(){
			?>
				</ul>
			</div>
		</nav>
			<!--<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
			  Launch demo modal
			</button>-->

			<!-- Modal -->


			<div class="modal fade bd-example-modal-lg" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
			  <div class="modal-dialog modal-lg" role="document">
			    <div class="modal-content">
			      <div id="modal-header" class="modal-header">
			        <h5 id="modal-title" class="modal-title" id="exampleModalLongTitle"></h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div id="modal-body" class="modal-body">
			        
			      </div>
			      <div class="modal-footer d-flex justify-content-start">
			      	<!--<i class="fas fa-heart text-danger" style="font-size:25px"></i>!-->
			      	<div>
			      		<a id="link-like" href="#"><i id="like" class="fas fa-heart text-primary" style="font-size:25px"></i></a>
			      		<p class="d-inline-block">Friend-Zoned </p>
			      		<script>
			      			$("#link-like").on("click",function(){
			      				if($("#like").hasClass("text-primary")){
			      					$("#like").removeClass("text-primary");
			      					$("#like").addClass("text-danger");
			      				}
			      				else{
			      					$("#like").removeClass("text-danger");
			      					$("#like").addClass("text-primary");
			      				}
			      			});
			      		</script>
			      	</div>
			        <button type="button" class="btn btn-primary mr-2" style="position: absolute; right:0" data-dismiss="modal">Quitter</button>
			      </div>
			    </div>
			  </div>
			</div>

			
			<script>

				function tabou(val){
					var input_val = $("#search-bar").val();
					var url = "http://51.83.71.83:8080/moteur/ServletHehe";
					var data = "search="+input_val;

					if(input_val.length>0 && (val.type=="click" || (val.type=="keyup" && val.key=="Enter"))){
						$.ajax({
							type: "GET",
							dataType: "json",
							url: url,
							data: data,
							success: function(data, statut){
								$("#article").empty();
								$("<p class='h5'>"+(Object.keys(data).length-2)+" résultats pour la recherche : "+input_val+"</p>").appendTo("#article");
								$.each( data, function( key, value ) {
									if(key!="found" && key!="success"){
										$('<div class="card mt-2 p-4"><p class="h2">'+value[0].title+'</p><p>'+value[0].date+'</p><button data-num="'+$.trim(key)+'" data-title="'+value[0].title+'" data-toggle="modal" data-target="#exampleModalLong" class="btn btn-primary col-xl-2 col-lg-2 col-md-3 col-sm-4 col-xs-5 issou">Consulter</button></div>').appendTo("#article");
									}

								});


								$(".issou").on("click",function(){
									var title = $(this).attr("data-title");
									console.log(title);
									$("#modal-title").text(title);
									$("#modal-body").empty();

									var url2 = "api.php";
									var data2 = "action=getTexte&doc="+$(this).attr("data-num");

									$.ajax({
										type: "GET",
										dataType: "json",
										url: url2,
										data: data2,
										success: function(data, statut){
											console.log(data);
											$("#modal-body").empty();
											var p = data.BODY.TEXT.P;
											if(p==null){
												p = data.BODY.TEXT;
												$("<div>"+p+"</div>").appendTo("#modal-body");
											}
											else{
												$.each(p, function(key, value){
													$("<div class='mt-3 p-1'>"+value+"</div>").appendTo("#modal-body");
												});
											}
										},
										error: function(d){
											console.log(d);
										}
									});

								});
							},
							error: function(d){
								console.log("Erreur fdp");
							}
						});
					}
				}

				$('#search-btn').on( "click", {act:"search"}, tabou);

				$('#search-bar').on( "keyup", {act:"search"}, tabou);

			</script>	
			<?php
		}

		public function displayMenuAtomic(string $link, string $name){
			echo "<ul 'nav-item'><li class='nav-item'><a class='nav-link' href=\"{$link}\">{$name}</a></li></ul>\n";
		}

		public function displayMenuPictureAtomic(string $link,string $path, string $alt, string $basedir=PICTURES.DS){
			echo "<li class='nav-item'><a class='nav-link' href=\"{$link}\"><img src=\"{$basedir}{$path}\" alt=\"{$alt}\"/></a></li>\n";
		}

		public function displayMenu(array $array){
			echo "<li class='navbar-nav'><a class='nav-link' href=\"{$array[0]}\">{$array[1]}</a></li>\n";
		}

		public function displayMenuPicture(array $array, string $basedir=PICTURES.DS){
			echo "<li><a href=\"{$array[0]}\"><img src=\"{$basedir}{$array[1]}\" alt=\"{$array[2]}\"/></a></li>\n";
		}
	}
?>
