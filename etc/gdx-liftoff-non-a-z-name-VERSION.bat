@echo OFF
(IF NOT EXIST "C:\gdx-liftoff\.temp" MKDIR "C:\gdx-liftoff\.temp") && SET TMP=C:\gdx-liftoff\.temp && (IF NOT EXIST "C:\gdx-liftoff\gdx-liftoff-VERSION.jar" COPY gdx-liftoff-VERSION.jar C:\gdx-liftoff\gdx-liftoff-VERSION.jar) && START /D C:\gdx-liftoff javaw -jar gdx-liftoff-VERSION.jar