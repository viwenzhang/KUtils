package com.kiven.sample.gl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLU
import android.opengl.Matrix
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.kiven.kutils.activityHelper.KActivityHelper
import com.kiven.kutils.activityHelper.KHelperActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

open class AHGL2Super : KActivityHelper(), GLSurfaceView.Renderer {
    override fun onDrawFrame(gl: GL10) {

    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        // Sets the current view port to the new size. 定义显示视窗的大小和位置
        GLES20.glViewport(0, 0, width, height)
        //计算GLSurfaceView的宽高比
        val ratio = width.toFloat() / height


        /*// Select the projection matrix, 设置当前 Matrix 模式为 Projection 投影矩阵
        GLES20.glMatrixMode(GL10.GL_PROJECTION)
        // Reset the projection matrix
        GLES20.glLoadIdentity()

        // 透视投影, https://blog.csdn.net/tyxkzzf/article/details/40921713
        // fovy是眼睛上下睁开的幅度，角度值，值越小. aspect表示裁剪面的宽w高h比. zNear表示近裁剪面到眼睛的距离，zFar表示远裁剪面到眼睛的距离
        GLU.gluPerspective(gl, 45.0f,
                width.toFloat() / height.toFloat(),
                0.1f, 100.0f)

        // Select the modelview matrix
        GLES20.glMatrixMode(GL10.GL_MODELVIEW)
        // 重置当前的模型观察矩阵。
        GLES20.glLoadIdentity()*/
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        // 设置清除屏幕时所用的颜色，参数对应(红,绿,蓝,Alpha值)。色彩值的范围从0.0f到1.0f。0.0f代表最黑的情况，1.0f就是最亮的情况。
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.5f)
        //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        /*// 启用smooth shading（阴影平滑）。阴影平滑通过多边形精细的混合色彩，并对外部光进行平滑
        GLES20.glShadeModel(GL10.GL_SMOOTH)

        // 下面三行是关于depth buffer(深度缓存)的。将深度缓存设想为屏幕后面的层。深度缓存不断的对物体进入屏幕内部有多深进行跟踪。
        // Depth buffer setup.
        GLES20.glClearDepthf(1.0f)
        // Enables depth testing.
        GLES20.glEnable(GL10.GL_DEPTH_TEST)
        // The type of depth testing to do.
        GLES20.glDepthFunc(GL10.GL_LEQUAL)

        // 这里告诉OpenGL我们希望进行最好的透视修正。这会十分轻微的影响性能。但使得透视图看起来好一点。
        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST)*/
    }

    /**
     * 编译OpenGLShading Language (GLSL)代码
     */
    fun loadShader(shaderType: Int, source: String):Int {
        //创建一个新shader
        var shader = GLES20.glCreateShader(shaderType)
        //若创建成功则加载shader
        if (shader != 0) {
            //加载shader的源代码
            GLES20.glShaderSource(shader, source)
            //编译shader
            GLES20.glCompileShader(shader)
            //存放编译成功shader数量的数组
            val compiled = IntArray(1)
            //获取Shader的编译情况
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)
            if (compiled[0] == 0) {//若编译失败则显示错误日志并删除此shader
                Log.e("ES20_ERROR", "Could not compile shader $shaderType:")
                Log.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader))
                GLES20.glDeleteShader(shader)
                shader = 0
            }
        }
        return shader
    }

    protected lateinit var surfaceView: GLSurfaceView

    protected var dragx = 0f
    protected var dragy = 0f
    override fun onCreate(activity: KHelperActivity, savedInstanceState: Bundle?) {
        super.onCreate(activity, savedInstanceState)
        surfaceView = object : GLSurfaceView(mActivity){
            var startx = 0f
            var starty = 0f
            override fun onTouchEvent(event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startx = event.x
                        starty = event.y
                    }
                    else -> {
                        dragx += event.x - startx
                        dragy += event.y - starty

                        startx = event.x
                        starty = event.y

//                        surfaceView.requestRender()
                    }
                }
                return true
            }
        }

        // 设置OpenGl ES的版本为2.0，需在manifests配置gl版本。配置后，居然不能绘图
        surfaceView.setEGLContextClientVersion(2)
        // 设置与当前GLSurfaceView绑定的Renderer
        surfaceView.setRenderer(this)

        // 设置渲染的模式，
        // RENDERMODE_WHEN_DIRTY：只有在调用 requestRender() 在更新屏幕，
        // RENDERMODE_CONTINUOUSLY：连续不断的更新屏幕
        surfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY


        setContentView(surfaceView)
    }

    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
    }
}