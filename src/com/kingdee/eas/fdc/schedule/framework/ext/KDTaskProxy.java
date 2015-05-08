package com.kingdee.eas.fdc.schedule.framework.ext;

import java.awt.Color;
import java.io.IOException;



public class KDTaskProxy {
	/*private static KDTaskGeneratorClassLoader classLoader = 
		new KDTaskGeneratorClassLoader();
	private static Class secureKDTaskClass;
	public static void main(String[] args) throws ClassFormatError, InstantiationException, IllegalAccessException {
		KDTask task=generateSecureKDTask();
		task.setColor(Color.red);
		
		System.out.println(task.getColor());
	}
	public static KDTask generateSecureKDTask() throws ClassFormatError, 
		InstantiationException, IllegalAccessException {
		if (null == secureKDTaskClass) {            
			ClassReader cr;
			try {
				cr = new ClassReader(KDTask.class.getName());
				ClassWriter cw = new ClassWriter(true);
				ClassAdapter classAdapter = new AddMethodHandlerClassAdapter(cw);
				cr.accept(classAdapter, false);
				byte[] data = cw.toByteArray();
				secureKDTaskClass = classLoader.defineClassFromClassFile(
						"com.kingdee.eas.fdc.schedule.framework.ext.KDTask$EnhancedByASM",data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (KDTask) secureKDTaskClass.newInstance();
	}

	private static class KDTaskGeneratorClassLoader extends ClassLoader {
		public Class defineClassFromClassFile(String className,
			byte[] classFile) throws ClassFormatError {
			return defineClass("com.kingdee.eas.fdc.schedule.framework.ext.KDTask$EnhancedByASM", classFile, 0, classFile.length);
		}
	}

}

class AddMethodHandlerClassAdapter extends ClassAdapter{
	private String enhancedSuperName;
	public AddMethodHandlerClassAdapter(ClassVisitor cv) {
		//Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
		//负责改写后代码的输出
		super(cv);
	}
	
	//重写 visitMethod，访问到 "setXXX" 方法时，
	//给出自定义 MethodVisitor，实际改写方法内容
	public MethodVisitor visitMethod(final int access, final String name,
		final String desc, final String signature, final String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,exceptions);
		MethodVisitor wrappedMv = mv;
		if (mv != null) {
			//对于 "operation" 方法
			if (name.startsWith("set")) { 
				//使用自定义 MethodVisitor，实际改写方法内容
				wrappedMv = new AddMethodHandleAdapter(mv); 
			}  else if (name.equals("<init>")) {
				wrappedMv = new ChangeToChildConstructorMethodAdapter(mv,
						enhancedSuperName);
			}

		}
		return wrappedMv;
	}
	
	public void visit(final int version, final int access, final String name,
			final String signature, final String superName,
			final String[] interfaces) {
		String enhancedName = name + "$EnhancedByASM";  //改变类命名
		enhancedSuperName = name; //改变父类，这里是”KDTask”
		super.visit(version, access, enhancedName, signature,
		enhancedSuperName, interfaces);
	}

}

class AddMethodHandleAdapter extends MethodAdapter {
	public AddMethodHandleAdapter(MethodVisitor mv) {
		super(mv);
	}

	public void visitCode() {
		System.out.println("set sxhong test");
		visitMethodInsn(Opcodes.INVOKESTATIC, "SecurityChecker",
			"checkSecurity", "()V");
	}
}   

class ChangeToChildConstructorMethodAdapter extends MethodAdapter {
	private String superClassName;

	public ChangeToChildConstructorMethodAdapter(MethodVisitor mv,
		String superClassName) {
		super(mv);
		this.superClassName = superClassName;
	}

	public void visitMethodInsn(int opcode, String owner, String name,
		String desc) {
		//调用父类的构造函数时
		if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) { 
			owner = superClassName;
		}
		super.visitMethodInsn(opcode, owner, name, desc);//改写父类为superClassName
	}*/
}


//动态ASM 原理：http://www.ibm.com/developerworks/cn/java/j-lo-asm30/
