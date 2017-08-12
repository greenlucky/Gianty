package controllers;

import models.Product;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.index;

import javax.inject.Inject;
import java.util.List;
import views.html.products.*;

/**
 * Created by lamdevops on 7/22/17.
 */
@With(CatchAction.class)
public class ProductController extends Controller {

    private static FormFactory formFactory;

    @Inject
    public ProductController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result index() {
        return redirect(routes.ProductController.list());
    }

    public Result list() {
        List<Product> products = Product.findAll();
        return ok(list.render(products));
    }

    public Result newProduct() {
        return ok(details.render(formFactory.form(Product.class)));
    }

    public Result details(String ean) {
        final Product product = Product.findByEan(ean);

        if(product == null)
            return notFound(String.format("Product %s does not exist", ean));
        Form<Product> filledForm = formFactory.form(Product.class).fill(product);
        System.out.println(filledForm);
        return ok(details.render(filledForm));
    }

    public Result save() {
        Form<Product> boundForm = formFactory.form(Product.class).bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }

        Product product = boundForm.get();
        product.save();
        flash("success", String.format("Successfully added product %s", product));

        return redirect(routes.ProductController.list());
    }

    public Result delete(String ean) {
        final Product product = Product.findByEan(ean);
        if(product == null)
            return notFound(String.format("Product %s does not exist.", ean));
        Product.remove(product);
        return redirect(routes.ProductController.list());
    }
}
