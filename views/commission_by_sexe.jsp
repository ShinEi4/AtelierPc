<!DOCTYPE html>
<html lang="fr">
<%@page import="models.*" %>
<%@page import="java.util.List" %>
<%
    List<Technicien> techniciens = (List<Technicien>)request.getAttribute("techniciens");
    String dateDebut = (String)request.getAttribute("dateDebut");
    String dateFin = (String)request.getAttribute("dateFin");
%>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
    <title>Commissions par Sexe - Atelier Ordinateur</title>
    <link rel="icon" href="assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

    <script src="assets/js/plugin/webfont/webfont.min.js"></script>
    <script>
      WebFont.load({
        google: { families: ["Public Sans:300,400,500,600,700"] },
        custom: {
          families: [
            "Font Awesome 5 Solid",
            "Font Awesome 5 Regular",
            "Font Awesome 5 Brands",
            "simple-line-icons",
          ],
          urls: ["assets/css/fonts.min.css"],
        },
        active: function () {
          sessionStorage.fonts = true;
        },
      });
    </script>
    
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/plugins.min.css" />
    <link rel="stylesheet" href="assets/css/kaiadmin.min.css" />
</head>
<body>
    <div class="wrapper">
        <div class="sidebar" data-background-color="dark">
        <div class="sidebar-wrapper scrollbar scrollbar-inner">
          <div class="sidebar-content">
            <div class="user">
              <div class="info">
                <h3 class="text-center text-primary"><i class="fas fa-tools me-2"></i>Atelier Ordinateur</h3>
              </div>
            </div>
            <ul class="nav nav-secondary">
              <!-- Menu Insertions -->
              <li class="nav-item">
                <a data-bs-toggle="collapse" href="#insertions">
                  <i class="fas fa-plus-circle"></i>
                  <p>Insertions</p>
                  <span class="caret"></span>
                </a>
                <div class="collapse" id="insertions">
                  <ul class="nav nav-collapse">
                    <li>
                      <a href="/ReparationOrdi/clientform">
                        <i class="fas fa-user-plus"></i>
                        <span class="sub-item">Client</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/typecomposantform">
                        <i class="fas fa-microchip"></i>
                        <span class="sub-item">Type Composant</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/composantform">
                        <i class="fas fa-memory"></i>
                        <span class="sub-item">Composant</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/marqueform">
                        <i class="fas fa-trademark"></i>
                        <span class="sub-item">Marque</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/modeleform">
                        <i class="fas fa-laptop"></i>
                        <span class="sub-item">Modele</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/ordinateurform">
                        <i class="fas fa-desktop"></i>
                        <span class="sub-item">Ordinateur</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/reparationform">
                        <i class="fas fa-wrench"></i>
                        <span class="sub-item">Reparation</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/stockform">
                        <i class="fas fa-warehouse"></i>
                        <span class="sub-item">Stock</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/technicienform">
                        <i class="fas fa-user-cog"></i>
                        <span class="sub-item">Technicien</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/categorieform">
                        <i class="fas fa-user-cog"></i>
                        <span class="sub-item">Categorie Modele</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/commission">
                        <i class="fas fa-user-cog"></i>
                        <span class="sub-item">Commission</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>

              <!-- Menu Listes -->
              <li class="nav-item">
                <a data-bs-toggle="collapse" href="#listes">
                  <i class="fas fa-list"></i>
                  <p>Listes</p>
                  <span class="caret"></span>
                </a>
                <div class="collapse" id="listes">
                  <ul class="nav nav-collapse">
                    <li>
                      <a href="/ReparationOrdi/clients">
                        <i class="fas fa-users"></i>
                        <span class="sub-item">Clients</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/typescomposants">
                        <i class="fas fa-microchip"></i>
                        <span class="sub-item">Types Composants</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/composants">
                        <i class="fas fa-memory"></i>
                        <span class="sub-item">Composants</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/marques">
                        <i class="fas fa-trademark"></i>
                        <span class="sub-item">Marques</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/modeles">
                        <i class="fas fa-laptop"></i>
                        <span class="sub-item">Modeles</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/ordinateurs">
                        <i class="fas fa-desktop"></i>
                        <span class="sub-item">Ordinateurs</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/reparations">
                        <i class="fas fa-wrench"></i>
                        <span class="sub-item">Reparations</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/stocks">
                        <i class="fas fa-warehouse"></i>
                        <span class="sub-item">Stocks</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/techniciens">
                        <i class="fas fa-user-cog"></i>
                        <span class="sub-item">Techniciens</span>
                      </a>
                    </li>
                    <li>
                      <a href="/ReparationOrdi/categoriesmodeles">
                        <i class="fas fa-user-cog"></i>
                        <span class="sub-item">Categorie Modele</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>

              <!-- Nouveau Menu Etat -->
              <li class="nav-item">
                <a data-bs-toggle="collapse" href="#etats">
                  <i class="fas fa-chart-bar"></i>
                  <p>Etats</p>
                  <span class="caret"></span>
                </a>
                <div class="collapse" id="etats">
                  <ul class="nav nav-collapse">
                    <li>
                      <a href="/ReparationOrdi/commissions-by-sexe">
                        <i class="fas fa-venus-mars"></i>
                        <span class="sub-item">Commissions par Sexe</span>
                      </a>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
        
        <div class="main-panel">
            <div class="content">
                <div class="page-inner">
                    <div class="page-header">
                        <h4 class="page-title">Commissions par Sexe</h4>
                    </div>
                    
                    <!-- Formulaire de recherche -->
                    <div class="card">
                        <div class="card-body">
                            <form action="commissions-by-sexe" method="get" class="form-inline">
                                <div class="form-group mx-sm-3 mb-2">
                                    <label for="dateDebut" class="mr-2">Du : </label>
                                    <input type="date" class="form-control" id="dateDebut" name="dateDebut" 
                                           value="<%= dateDebut != null ? dateDebut : "" %>">
                                </div>
                                <div class="form-group mx-sm-3 mb-2">
                                    <label for="dateFin" class="mr-2">Au : </label>
                                    <input type="date" class="form-control" id="dateFin" name="dateFin"
                                           value="<%= dateFin != null ? dateFin : "" %>">
                                </div>
                                <button type="submit" class="btn btn-primary mb-2">Rechercher</button>
                            </form>
                        </div>
                    </div>

                    <!-- Tableau des résultats -->
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">
                                Commissions totales par sexe 
                                <% if(dateDebut != null && dateFin != null) { %>
                                    du <%= dateDebut %> au <%= dateFin %>
                                <% } %>
                            </h4>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Sexe</th>
                                            <th>Commission Totale</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for(Technicien tech : techniciens) { %>
                                        <tr>
                                            <td><%= tech.getSexe().getNom() %></td>
                                            <td><%= tech.getTotalCommission() %> Ar</td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>
    <script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
    <script src="assets/js/kaiadmin.min.js"></script>
</body>
</html> 