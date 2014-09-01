package org.nocket;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

/**
 * This class has only one meaningful implementation. It is the <code>checkExit</code> method.
 * All other overridden <code>checkXXX</code> methods do nothing. 
 * They don't call super as it may produce unintentional side effects.
 * 
 * {@link System#getSecurityManager()} usually returns <code>null</code> so we 
 * provide empty implementations to mimic null behavior. 
 * 
 * @author Maxim Zaks
 *
 */
class DMDWebSecurityManager extends SecurityManager {
	
	@Override
	public void checkExit(int status) {
		throw new SecurityException("You can't use System.exit in DMDWeb Application.");
	}

	@Override
	public void checkAccept(String host, int port) {
	}

	@Override
	public void checkAccess(Thread t) {
	}

	@Override
	public void checkAccess(ThreadGroup g) {
	}

	@Override
	public void checkAwtEventQueueAccess() {
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
	}

	@Override
	public void checkConnect(String host, int port) {
	}

	@Override
	public void checkCreateClassLoader() {
	}

	@Override
	public void checkDelete(String file) {
	}

	@Override
	public void checkExec(String cmd) {
	}

	@Override
	public void checkLink(String lib) {
	}

	@Override
	public void checkListen(int port) {
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
	}

	@Override
	public void checkMulticast(InetAddress maddr, byte ttl) {
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
	}

	@Override
	public void checkPackageAccess(String pkg) {
	}

	@Override
	public void checkPackageDefinition(String pkg) {
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
	}

	@Override
	public void checkPermission(Permission perm) {
	}

	@Override
	public void checkPrintJobAccess() {
	}

	@Override
	public void checkPropertiesAccess() {
	}

	@Override
	public void checkPropertyAccess(String key) {
	}

	@Override
	public void checkRead(FileDescriptor fd) {
	}

	@Override
	public void checkRead(String file, Object context) {
	}

	@Override
	public void checkRead(String file) {
	}

	@Override
	public void checkSecurityAccess(String target) {
	}

	@Override
	public void checkSetFactory() {
	}

	@Override
	public void checkSystemClipboardAccess() {
	}

	@Override
	public boolean checkTopLevelWindow(Object window) {
		return super.checkTopLevelWindow(window);
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
	}

	@Override
	public void checkWrite(String file) {
	}
	
	
}