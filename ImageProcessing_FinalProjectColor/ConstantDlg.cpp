// ConstantDlg.cpp : ���� �����Դϴ�.
//

#include "stdafx.h"
#include "FinalProjectColor.h"
#include "ConstantDlg.h"
#include "afxdialogex.h"


// CConstantDlg ��ȭ �����Դϴ�.

IMPLEMENT_DYNAMIC(CConstantDlg, CDialog)

CConstantDlg::CConstantDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CConstantDlg::IDD, pParent)
	, intValue(0)
{

}

CConstantDlg::~CConstantDlg()
{
}

void CConstantDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, intValue);
}


BEGIN_MESSAGE_MAP(CConstantDlg, CDialog)
END_MESSAGE_MAP()


// CConstantDlg �޽��� ó�����Դϴ�.