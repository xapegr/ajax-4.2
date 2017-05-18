function Product() {
    //Attributes declaration
    this.id;
    this.name;
    this.price;

    //methods declaration
    this.construct = function (id, name, price) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
    }

    //setters
    this.setId = function (id) {
        this.id = id;
    }
    this.setName = function (name) {
        this.name = name;
    }
    this.setPrice = function (price) {
        this.price = price;
    }

    //getters
    this.getId = function () {
        return this.id;
    }
    this.getName = function () {
        return this.name;
    }
    this.getPrice = function () {
        return this.price;
    }

    this.toString = function () {
        var productString = "id=" + this.getId() + " name=" + this.getName() + " price=" + this.getPrice();

        return productString;
    }
}
