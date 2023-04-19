package dk.sdu.sesem4.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.mockito.Mockito;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.*;

/**
 * @author Jakob L.M. & Jon F.J.
 * This class is a JUnit runner that can be used to run tests on a headless version of a libGDX application.
 * It extends BlockJUnit4ClassRunner and implements ApplicationListener.
 */
public class GdxTestRunner extends BlockJUnit4ClassRunner implements ApplicationListener {
	private final Map<FrameworkMethod, RunNotifier> invokeInRender = new HashMap<>();

	/**
	 * Constructs a new GdxTestRunner.
	 * The constructor creates a new HeadlessApplicationConfiguration object and a new HeadlessApplication object,
	 * passing in the (this.) object and the conf object as parameters.
	 * The Gdx.gl variable is then set to a mock GL20 object using the Mockito.mock method from the Mockito library.
	 * @param klass The class to be run.
	 * @throws InitializationError If there is an error initializing the runner.
	 */
	public GdxTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
		HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

		new HeadlessApplication(this, conf);
		Gdx.gl = Mockito.mock(GL20.class);
	}

	@Override
	public void create() {
	}

	@Override
	public void resume() {
	}

	/**
	 * This method runs JUnit tests that were added to the invokeInRender map.
	 * It first synchronizes on the invokeInRender object to ensure that the method is thread-safe.
	 * It then loops through the entries in the invokeInRender map, calling super.runChild
	 * for each entry to run the JUnit test. Finally, the invokeInRender map is cleared.
	 */
	@Override
	public void render() {
		synchronized (invokeInRender) {
			for (Map.Entry<FrameworkMethod, RunNotifier> each : invokeInRender.entrySet()) {
				super.runChild(each.getKey(), each.getValue());
			}
			invokeInRender.clear();
		}
	}

	/**
	 * Called when the screen is resized.
	 * @param width the new screen width in pixels
	 * @param height the new screen height in pixels
	 */
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
	}

	/**
	 * Runs a single test method in the render thread.
	 * <p>
	 * This method is called by the {@link #render()} method to run tests that were added to the {@code invokeInRender} map.
	 * It runs the test method specified by the {@code method} parameter using the {@code notifier} parameter to report the
	 * test results.
	 * </p>
	 * <p>
	 * This method is synchronized on the {@code invokeInRender} object to ensure that it is thread-safe.
	 * </p>
	 *
	 * @param method   the test method to run
	 * @param notifier the {@link RunNotifier} to report the test results to
	 */
	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		synchronized (invokeInRender) {
			// add for invoking in render phase, where gl context is available
			invokeInRender.put(method, notifier);
		}
		// wait until that test was invoked
		waitUntilInvokedInRenderMethod();
	}

	/**
	 * Waits until the test has been invoked in the render method.
	 * <p>
	 * This method is called by the {@link #runChild(FrameworkMethod, RunNotifier)} method to wait until the test has been
	 * added to the {@code invokeInRender}.
	 * </p>
	 * <p>
	 * This method is synchronized on the {@code invokeInRender} object to ensure that it is thread-safe.
	 * </p>
	 */
	private void waitUntilInvokedInRenderMethod() {
		try {
			while (true) {
				Thread.sleep(10);
				synchronized (invokeInRender) {
					if (invokeInRender.isEmpty())
						break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}