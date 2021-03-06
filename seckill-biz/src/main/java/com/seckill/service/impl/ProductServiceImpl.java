package com.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.seckill.entity.input.QueryProductInput;
import com.seckill.entity.vo.ReturnMsgVo;
import com.seckill.service.IProductService;
import com.seckill.service.backend.api.ProductInfo;
import com.seckill.service.log.LogUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: Bojun Ji
 * @Description: get product detail from interface by RPC
 * @Date: 2018/8/12_1:19 AM
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Reference
    private com.seckill.service.backend.api.IProductService IProductService;

    /**
     * get product info from service interface
     *
     * @param queryProductInput query product input from front end
     * @return
     */
    @Override
    public ReturnMsgVo queryProduct(QueryProductInput queryProductInput) {
        ReturnMsgVo<ProductInfo> tmpResult = new ReturnMsgVo<>();
        tmpResult.setErrorMessage("the request is null");
        tmpResult.setResponse(null);
        tmpResult.setResult(false);
        if (queryProductInput == null) {
            LogUtil.logError(this.getClass(), "the request is null");
            return tmpResult;
        }
        ProductInfo productInfo = IProductService.queryProductInfo(queryProductInput.getProductId());
        if (productInfo != null) {
            LogUtil.logInfo(this.getClass(), String.format("product: %s, get data successfully, product detail: %s", queryProductInput, productInfo));
            tmpResult.setErrorMessage(null);
            tmpResult.setResponse(productInfo);
            tmpResult.setResult(true);
        } else {
            LogUtil.logError(this.getClass(), String.format("product: %s, get data failed", queryProductInput));
            tmpResult.setErrorMessage(String.format("product: %s, get data failed", queryProductInput));
        }
        return tmpResult;
    }
}
