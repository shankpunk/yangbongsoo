// ConstantDlg2.cpp : 구현 파일입니다.
//

#include "stdafx.h"
#include "FinalProjectColor.h"
#include "ConstantDlg2.h"
#include "afxdialogex.h"


// CConstantDlg2 대화 상자입니다.

IMPLEMENT_DYNAMIC(CConstantDlg2, CDialog)

CConstantDlg2::CConstantDlg2(CWnd* pParent /*=NULL*/)
	: CDialog(CConstantDlg2::IDD, pParent)
	, xValue(0)
	, yValue(0)
{

}

CConstantDlg2::~CConstantDlg2()
{
}

void CConstantDlg2::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, xValue);
	DDX_Text(pDX, IDC_EDIT2, yValue);
}


BEGIN_MESSAGE_MAP(CConstantDlg2, CDialog)
END_MESSAGE_MAP()


// CConstantDlg2 메시지 처리기입니다.
