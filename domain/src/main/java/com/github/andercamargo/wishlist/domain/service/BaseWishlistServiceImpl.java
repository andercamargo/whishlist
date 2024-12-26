package com.github.andercamargo.wishlist.domain.service;


import com.github.andercamargo.wishlist.data.model.Product;
import com.github.andercamargo.wishlist.data.model.Wishlist;
import com.github.andercamargo.wishlist.data.repository.WishListRepository;
import com.github.andercamargo.wishlist.domain.dto.ResponseDTO;
import com.github.andercamargo.wishlist.domain.dto.WishlistDTO;
import com.github.andercamargo.wishlist.domain.exception.ValidateException;
import com.github.andercamargo.wishlist.domain.service.interfaces.IWhishlistService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Objects;

import static com.github.andercamargo.wishlist.domain.enumeration.ResponseMessageApiEnum.OPERACAO_ERRO;
import static com.github.andercamargo.wishlist.domain.enumeration.ResponseMessageApiEnum.OPERACAO_SUCESSO;
import static com.github.andercamargo.wishlist.domain.enumeration.ResponseMessageApiEnum.PRODUTO_NAO_ENCONTRADO;
import static com.github.andercamargo.wishlist.domain.enumeration.ResponseMessageApiEnum.WISHLIST_NAO_ENCONTRADO;

@Service
@Log4j2
@SuppressWarnings("unchecked")
public abstract class BaseWishlistServiceImpl implements IWhishlistService<WishlistDTO> {

    @Autowired
    private WishListRepository wishlistRepository;
    @Autowired
    private ResponseService responseService;


    @Override
    public ResponseEntity listAll(Integer customerId) {

        ResponseEntity responseEntity = null;
        ResponseDTO response = new ResponseDTO();

        try {
            log.info("Iniciando a pesquisa listAll através do cliente {}",
                    customerId);

            log.info("Pesquisando listAll do wishList");

            var wishlists = wishlistRepository.findAllByCostumer(customerId);

            log.info("Pesquisa listAll finalizada com sucesso.");

            if(wishlists == null){
                response = this.responseService.covertResponse(HttpStatus.NOT_FOUND.value(), WISHLIST_NAO_ENCONTRADO.getValor(), null) ;
                return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));
            }

            response = this.responseService.covertResponse(HttpStatus.OK.value(), OPERACAO_SUCESSO.getValor(), wishlists) ;

        }catch (Exception e){
            log.error("Ocorreu um erro na operacao de listAll", e);
            response = this.responseService.covertResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), OPERACAO_ERRO.getValor(), null);
        }

        return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @Override
    public ResponseEntity add(WishlistDTO wishlistDTO) {
        ResponseDTO response = new ResponseDTO();
        ResponseEntity responseEntity = null;

        try{
            Wishlist wishList = new Wishlist();
            log.info("Iniciando o add do wishList");

            log.info("Obtendo wishlist previa");
            wishList = this.wishlistRepository.findAllByCostumer(Integer.valueOf(wishlistDTO.getCustomer().getId()));

            if(wishList != null){
                log.info("Wishlist encontrada {}", wishList.getId());
                log.info("Realizando validações");
                this.validate(wishList, wishlistDTO);
                wishList.getProducts().add(wishlistDTO.getProduct().convert(wishlistDTO.getProduct()));
                log.info("Atualizando o wishList {}", wishList.toString());
                wishList = wishlistRepository.update(wishList);
            }else {
                log.info("Convertendo o dto do wishList");
                wishList  = wishlistDTO.convert(wishlistDTO);
                log.info("Persistindo o wishList {}", wishList.toString());
                wishList = wishlistRepository.save(wishList);
            }

            response = this.responseService.covertResponse(HttpStatus.CREATED.value(), OPERACAO_SUCESSO.getValor(), wishList) ;
            log.info("WishList inserido com sucesso: id {}.", wishList.getId());
        }catch (ValidateException e){
            log.error("Ocorreu um erro na operacao de add", e);
            response = this.responseService.covertResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        }
        catch (Exception e){
            log.error("Ocorreu um erro na operacao de add", e);
            response = this.responseService.covertResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), OPERACAO_ERRO.getValor(), null);
        }

        return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @Override
    public ResponseEntity find(Integer productId, Integer customerId) {
        ResponseDTO response = new ResponseDTO();
        ResponseEntity responseEntity = null;

        try {
            log.info("Iniciando a pesquisa através do produto {}, cliente {}",
                    productId,
                    customerId);

            log.info("Pesquisando o wishList");

            var wishlistDb = wishlistRepository.findByProductIdAndCustomer(productId, customerId);

            log.info("Pesquisa finalizada com sucesso.");

            if(wishlistDb == null){
                response = this.responseService.covertResponse(HttpStatus.NOT_FOUND.value(), PRODUTO_NAO_ENCONTRADO.getValor(), null) ;
                return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));
            }

            var products = new LinkedList<Product>();
            products.add(wishlistDb.getProducts().stream().filter(x-> Objects.equals(x.getId(), productId)).findFirst().orElse(null));
            wishlistDb.setProducts(null);
            wishlistDb.setProducts(products);

            response = this.responseService.covertResponse(HttpStatus.OK.value(), OPERACAO_SUCESSO.getValor(), wishlistDb) ;

        }catch (Exception e){
            log.error("Ocorreu um erro na operacao de find", e);
            response = this.responseService.covertResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), OPERACAO_ERRO.getValor(), null);
        }

        return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));
    }

    @Override
    public ResponseEntity remove(Integer productId, Integer customerId) {
        ResponseDTO response = new ResponseDTO();
        ResponseEntity responseEntity = null;

       try{
           log.info("Iniciando a exclusão através do produto {}, cliente {}",
                   productId,
                   customerId);

           var wishList = this.wishlistRepository.findByProductIdAndCustomer(productId, customerId);

           if(wishList == null){
               response = this.responseService.covertResponse(HttpStatus.NOT_FOUND.value(), PRODUTO_NAO_ENCONTRADO.getValor(), null) ;
               return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));
           }

           var item = wishList.getProducts().stream().filter(x-> x.getId() == productId).findAny().orElse(null);

           wishList.getProducts().remove(item);

           if(wishList.getProducts().isEmpty()){
               this.wishlistRepository.delete(wishList);
           } else {
               this.wishlistRepository.update(wishList);
           }

           response = this.responseService.covertResponse(HttpStatus.OK.value(), OPERACAO_SUCESSO.getValor(), null) ;

           log.info("Exclusão efetuada com sucesso.");

       } catch (Exception e){
           log.error("Ocorreu um erro na operacao de delete", e);
           response = this.responseService.covertResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), OPERACAO_ERRO.getValor(), null);
       }

        return new ResponseEntity(response, HttpStatusCode.valueOf(response.getCode()));

    }


    private void validate(Wishlist wishlist, WishlistDTO wishlistDTO) throws Exception{
        if(wishlist.getProducts().stream()
                .filter(x-> x.getId() == Integer.parseInt(wishlistDTO.getProduct().getId()))
                .findAny().orElse(null) != null){
            throw new ValidateException("O produto com id informado já está na wishlist");
        }
        else if(wishlist.getProducts().stream()
                .filter(x-> x.getName().
                        equals(wishlistDTO.getProduct().getName())
                )
                .findAny().orElse(null) != null){
            throw new ValidateException("O produto com nome já está na wishlist");
        }
        else if(wishlist.getProducts().size() == 20){
            throw new ValidateException("A wishlist está com a capacidade máxima de 20 itens");
        }
    }
}
