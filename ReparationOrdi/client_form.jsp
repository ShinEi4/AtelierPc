<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta content="width=device-width, initial-scale=1.0, shrink-to-fit=no" name="viewport" />
    <title>Atelier Ordinateur</title>
    <link rel="icon" href="assets/img/kaiadmin/favicon.ico" type="image/x-icon" />

    <!-- Fonts and icons -->
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

    <!-- CSS Files -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/plugins.min.css" />
    <link rel="stylesheet" href="assets/css/kaiadmin.min.css" />
  </head>
  <body>
    <div class="wrapper">
      <!-- Sidebar -->
      <!-- Copier tout le sidebar de index.jsp -->
      
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
            </ul>
          </div>
        </div>
      </div>

      <div class="main-panel">
        <div class="content">
          <div class="page-inner">
            <div class="page-header">
              <h4 class="page-title">Client Form</h4>
              <ul class="breadcrumbs">
                <li class="nav-home">
                  <a href="index.jsp">
                    <i class="icon-home"></i>
                  </a>
                </li>
                <li class="separator">
                  <i class="icon-arrow-right"></i>
                </li>
                <li class="nav-item">
                  <a href="#">Client</a>
                </li>
                <li class="separator">
                    <i class="icon-arrow-right"></i>
                </li>
                <li class="nav-item">
                    <a href="#">Form</a>
                </li>
            </ul>
            </div>
            <div class="page-category">
              <!-- Contenu du formulaire -->
              <div class="row">
                <div class="col-md-6">
                  <div class="card">
                    <div class="card-header">
                      <h4 class="card-title">Nouveau Client</h4>
                    </div>
                    <div class="card-body">
                      <form action="/ReparationOrdi/clientform" method="post">
                        <div class="form-group">
                          <label for="nom">Nom du client</label>
                          <input type="text" class="form-control" id="nom" name="nom" placeholder="Entrer le nom du client" required>
                        </div>
                        <div class="form-group">
                          <label for="numero">Numero telephone</label>
                          <input type="text" class="form-control" id="numero" name="num" placeholder="Entrer le numero de telephone" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Valider</button>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Footer -->
        <!-- Copier le footer de index.jsp -->
      </div>
    </div>

    <!--   Core JS Files   -->
    <script src="assets/js/core/jquery-3.7.1.min.js"></script>
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>

    <!-- jQuery Scrollbar -->
    <script src="assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>

    <!-- Kaiadmin JS -->
    <script src="assets/js/kaiadmin.min.js"></script>
  </body>
</html>