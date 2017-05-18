function Purchase() {
    //Attributes declaration
    this.id;
    this.idUser;
    this.idProduct;
    this.deliveryDate;
    this.specialRequests = new Array();
    this.specialInstructions;

    //methods declaration
    this.construct = function (id, idUser, idProduct, deliveryDate, specialRequests, specialInstructions) {
        this.setId(id);
        this.setIdUser(idUser);
        this.setIdProduct(idProduct);
        this.setDeliveryDate(deliveryDate);
        this.setSpecialRequests(specialRequests);
        this.setSpecialInstructions(specialInstructions);
    }

    //setters
    this.setId = function (id) {
        this.id = id;
    }
    this.setIdUser = function (idUser) {
        this.idUser = idUser;
    }
    this.setIdProduct = function (idProduct) {
        this.idProduct = idProduct;
    }
    this.setDeliveryDate = function (deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    this.setSpecialRequests = function (specialRequests) {
        this.specialRequests = specialRequests;
    }
    this.setSpecialInstructions = function (specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    //getters
    this.getId = function () {
        return this.id;
    }
    this.getIdUser = function () {
        return this.idUser;
    }
    this.getIdProduct = function () {
        return this.idProduct;
    }
    this.getDeliveryDate = function () {
        return this.deliveryDate;
    }
    this.getSpecialRequests = function () {
        return this.specialRequests;
    }
    this.getSpecialInstructions = function () {
        return this.specialInstructions;
    }

    this.addSpecialRequests = function (specialreq) {
        this.specialRequests.push(specialreq);
    }

    this.removeSpecialRequests = function (specialreq) {
        for (var i = 0; i < this.getSpecialRequests().length; i++) {
            if (this.getSpecialRequests()[i] == specialreq) {
                this.specialRequests.splice(i, 1);
                break;
            }
        }
    }

    this.toString = function () {
        var purchaseString = "id=" + this.getId() + " idUser=" + this.getIdUser() + " idProduct=" + this.getIdProduct();
        purchaseString += " deliveryDate=" + this.getDeliveryDate() + " specialRequests=" + this.getSpecialRequests();
        purchaseString += " specialInstructions=" + this.getSpecialInstructions();

        return purchaseString;
    }
}
