package com.github.czyzby.autumn.mvc.component.asset.dto.injection;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.czyzby.autumn.mvc.component.asset.AssetService;
import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
import com.github.czyzby.kiwi.util.gdx.reflection.Reflection;

/** Handles delayed asset injection into {@link Array} field.
 *
 * @author MJ */
public class ArrayAssetInjection implements AssetInjection {
    private final String[] assetPaths;
    private final Class<?> assetType;
    private final Field field;
    private final Object component;

    public ArrayAssetInjection(final String[] assetPaths, final Class<?> assetType, final Field field,
            final Object component) {
        this.assetPaths = assetPaths;
        this.assetType = assetType;
        this.field = field;
        this.component = component;
    }

    @Override
    public boolean inject(final AssetService assetService) {
        for (final String assetPath : assetPaths) {
            if (!assetService.isLoaded(assetPath)) {
                return false;
            }
        }
        injectAssets(assetService);
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void injectAssets(final AssetService assetService) {
        try {
            Array array = (Array) Reflection.getFieldValue(field, component);
            if (array == null) {
                array = GdxArrays.newArray();
            }
            for (final String assetPath : assetPaths) {
                array.add(assetService.get(assetPath, assetType));
            }
            Reflection.setFieldValue(field, component, array);
        } catch (final ReflectionException exception) {
            throw new GdxRuntimeException("Unable to inject array of assets into component: " + component + ".",
                    exception);
        }
    }

    @Override
    public void fillScheduledAssets(final ObjectSet<String> scheduledAssets) {
        for (final String assetPath : assetPaths) {
            scheduledAssets.add(assetPath);
        }
    }

    @Override
    public void removeScheduledAssets(final ObjectSet<String> scheduledAssets) {
        for (final String assetPath : assetPaths) {
            scheduledAssets.remove(assetPath);
        }
    }
}
