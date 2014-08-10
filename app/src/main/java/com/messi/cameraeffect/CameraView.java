package com.messi.cameraeffect;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by messi on 2014/08/06.
 */
class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera; // カメラ本体

    // ////////////////////////////////////////////////////////////
    // コンストラクタ
    public CameraView(Context context) {
        super(context);
        // TODO 自動生成されたコンストラクター・スタブ
        SurfaceHolder holder = this.getHolder();
        // これしないと落ちるんだってさ。でもAndroid4.0.2だと落ちないんだけど
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // コールバックとして登録する
        holder.addCallback(this);
    }



    // ////////////////////////////////////////////////////////////
    // サーフェイスが作られたときに呼ばれる
    public void surfaceCreated(SurfaceHolder holder) {

        // カメラを開く
        this.mCamera = Camera.open();

        // カメラが正常に開かれた
        if (this.mCamera != null) {
            // プレビューを設定するSurfaceHolderを設定
            try {
                this.mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // ////////////////////////////////////////////////////////////
    // サーフェイスサイズが変更されたときとかに呼ばれる
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

        // プレビューを止める
        this.mCamera.stopPreview();

        // カメラの情報を取り出す
        Camera.Parameters params = this.mCamera.getParameters();
        mCamera.setDisplayOrientation(90);

        int tmpWidth = 0;
        int prevWidth = width;
        int prevHeight = height;


        // カメラがサポートしているプレビューイメージのサイズ
        List<Camera.Size> sizeList = params.getSupportedPreviewSizes();

        // カメラに設定されているサポートされているサイズを一通りチェックする
        for (Camera.Size currSize : sizeList) {

            // プレビューするサーフェイスサイズより大きいものは無視する
            if ((prevWidth < currSize.width) ||
                    (prevHeight < currSize.height)) {
                continue;
            }

            // プレビューサイズの中で一番大きいものを選ぶ
            if (tmpWidth < currSize.width) {
                tmpWidth = currSize.width;
                prevWidth = currSize.width;
                prevHeight = currSize.height;
            }

        }
        Log.v("size", prevWidth + ", " + prevHeight);
        // プレビューサイズをカメラのパラメータにセットする
        params.setPreviewSize(prevWidth, prevHeight);
        params.setRotation(90);

//        // 実際のプレビュー画面への拡大率を設定する
//        float wScale = width / prevWidth;
//        float hScale = height / prevHeight;
//
//        // 画面内に収まらないといけないから拡大率は幅と高さで小さい方を採用する
//        float prevScale = wScale < hScale ? wScale : hScale;
//
//        // SurfaceViewのサイズをセットする
//        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
//        layoutParams.width = (int)(prevWidth * prevScale);
//        layoutParams.height = (int)(prevHeight * prevScale);
//
//
//        // レイアウトのサイズを設定し直して画像サイズに一致するようにする
//        // 一致させないと変な感じに画像がのびちゃう
//        this.setLayoutParams(layoutParams);

        // カメラにプレビューの設定情報を戻してプレビューを再開する
        this.mCamera.setParameters(params);
        this.mCamera.startPreview();

    }

    // ////////////////////////////////////////////////////////////
    // サーフェイスが破棄される問いに呼ばれる
    public void surfaceDestroyed(SurfaceHolder holder) {

        // カメラがある
        if (this.mCamera != null) {
            // プレビューの終了処理
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }

    }

}

